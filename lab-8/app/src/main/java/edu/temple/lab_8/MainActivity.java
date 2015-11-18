package edu.temple.lab_8;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText urlInput;
    private Spinner intervalInput;
    private Button watchButton;
    private FileWatcher fileWatcher;
    private boolean connected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = (EditText) findViewById(R.id.url);
        intervalInput = (Spinner) findViewById(R.id.interval);
        watchButton = (Button) findViewById(R.id.watch);

        urlInput.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);
        watchButton.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);

        String[] values = getResources().getStringArray(R.array.interval_options);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,values);
        intervalInput.setAdapter(spinAdapter);

        Intent intent = new Intent(MainActivity.this,FileWatcher.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = intervalInput.getSelectedItemPosition();
                String url = urlInput.getText().toString();

                if (url == null || url.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter a URL", Toast.LENGTH_SHORT).show();
                }

                if (time == 0) {
                    Toast.makeText(getBaseContext(), "Pick an interval", Toast.LENGTH_SHORT).show();
                } else if (time == 1) {
                    time = 60;
                } else if (time == 2) {
                    time = 300;
                } else if (time == 3) {
                    time = 600;
                } else {
                    time = 900;
                }

                if (time != 0) {
                    url = cleanUrl(url);

                    fileWatcher.watchNew(url, time);

                    intervalInput.setSelection(0);
                    urlInput.setText("");

                    Toast.makeText(getBaseContext(), "Added to the watchlist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            FileWatcher.FileWatchBinder binder = (FileWatcher.FileWatchBinder) service;
            fileWatcher = binder.getService();
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            connected = false;
        }
    };

    @Override
    public void onDestroy(){
        if(connected){
            unbindService(connection);
        }
        super.onDestroy();
    }

    public String cleanUrl(String url){
        url = url.toLowerCase();

        if(url.startsWith("http://") || url.startsWith("https://")){
            return url;
        }

        return "http://" + url;
    }
}
