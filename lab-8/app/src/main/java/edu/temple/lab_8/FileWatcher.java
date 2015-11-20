package edu.temple.lab_8;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by samcouch on 11/9/15.
 */
public class FileWatcher extends Service {
    private int NOTIFICATION_ID = 678912345;
    private NotificationManager notificationManager;

    private final IBinder fileWatchBinder = new FileWatchBinder();

    private ArrayList<WatchedFile> watchList;
    private ArrayList<String> updatedFiles;

    private int localInterval;
    private long lastCheck;

    public class FileWatchBinder extends Binder{
        FileWatcher getService(){
            return FileWatcher.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        watchList = new ArrayList<>();
        updatedFiles = new ArrayList<>();
        localInterval = -1;
        lastCheck = 0;
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Thread thread = new Thread(){
            @Override
            public void run(){
                diffAll();
            }
        };

        thread.start();

        return fileWatchBinder;
    }

    public void watchNew(String file, int checkTime){
        boolean newFile = true;

        for(WatchedFile watched : watchList){
            if(watched.url.equals(file)){
                newFile = false;
            }
        }

        if(newFile) {
            WatchedFile nWatch = new WatchedFile(file);
            diff(nWatch);
            watchList.add(nWatch);
        }

        if(this.localInterval != checkTime){
            this.localInterval = checkTime;
        }
    }

    private void diffAll(){
        while(true) {
            if (!watchList.isEmpty() && (System.currentTimeMillis() > lastCheck+ (localInterval * 1000))) {

                lastCheck = System.currentTimeMillis();

                for (WatchedFile file : watchList) {
                    diff(file);
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }  else{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void diff(WatchedFile fileToCheck){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            URL url = new URL(fileToCheck.url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String response = "";

            String tmpResponse = reader.readLine();

            while(tmpResponse != null){
                response = response + tmpResponse;
                tmpResponse = reader.readLine();
            }

            md.update(response.getBytes(), 0, response.length());
            byte[] newFileHash = md.digest();



            if(!fileToCheck.hash.equals(newFileHash)){
                fileToCheck.hash = newFileHash;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                String currTime = sdf.format(new Date());
                updatedFiles.add(fileToCheck.url + ":: updated at: " + currTime);
                notifyUser(fileToCheck.url);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void notifyUser(String file) {
        Intent intent = new Intent(this, StatusActivity.class);
        intent.putExtra("FILE_UPDATES", updatedFiles);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setTicker(file)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("FileWatcher")
                .setContentText("There were updates to one or more files that you are watching")
                .setContentIntent(contentIntent)
                .build();

        notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(NOTIFICATION_ID);
        super.onDestroy();
    }
}
