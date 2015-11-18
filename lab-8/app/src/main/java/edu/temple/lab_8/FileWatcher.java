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
 * Created by samcouch on 11/11/15.
 */
public class FileWatcher extends Service {
    private int NOTIFICATION = 678912345;
    private final IBinder fileWatchBinder = new FileWatchBinder();

    private ArrayList<String> watchList;
    private ArrayList<String> updates;
    private Map<String,byte[]> byteCheck;
    private int localInterval;
    private NotificationManager notificationManager;
    private long lastCheck;

    @Override
    public IBinder onBind(Intent intent) {
        watchList = new ArrayList<>();
        byteCheck = new HashMap<>();
        updates = new ArrayList<>();
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
        if(!watchList.contains(file)) {
            diff(file);
            watchList.add(file);
        }

        if(this.localInterval != checkTime){
            this.localInterval = checkTime;
        }
    }

    private void diffAll(){

        while(true) {
            if (!watchList.isEmpty() && (System.currentTimeMillis()>lastCheck+(localInterval * 1000))) {
                lastCheck = System.currentTimeMillis();
                for (String file : watchList) {
                    diff(file);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void diff(String newUrl){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            URL url = new URL(newUrl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String response = "",tmpResponse;

            tmpResponse = reader.readLine();
            while(tmpResponse != null){
                response = response + tmpResponse;
                tmpResponse = reader.readLine();
            }

            md.update(response.getBytes(),0,response.length());
            byte[] temp = md.digest();

            if(byteCheck.containsKey(newUrl)){
                if(!Arrays.equals(byteCheck.get(newUrl), temp)){
                    byteCheck.put(newUrl, temp);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                    String currTime = sdf.format(new Date());
                    updates.add(newUrl +" "+ currTime);
                    showNotification(newUrl);
                }
            }
            else{
                byteCheck.put(newUrl, temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showNotification(String file) {
        Intent intent = new Intent(this, StatusActivity.class);
        intent.putExtra("FILE_UPDATES", updates);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setTicker(file)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("FileWatcher")
                .setContentText("There were updates to one or more files you are watching")
                .setContentIntent(contentIntent)
                .build();

        notificationManager.cancel(NOTIFICATION);
        notificationManager.notify(NOTIFICATION, notification);
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        notificationManager.cancel(NOTIFICATION);
        super.onDestroy();
    }


    public class FileWatchBinder extends Binder{
        FileWatcher getService(){
            return FileWatcher.this;
        }
    }
}
