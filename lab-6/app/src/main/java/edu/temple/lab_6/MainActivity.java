package edu.temple.lab_6;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView countdown;
    TextView units;
    EditText seconds;
    Button action;
    Integer flags;
    Integer toCount;
    Thread t;
    Object mWait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdown = (TextView) findViewById(R.id.textView);
        units = (TextView) findViewById(R.id.textView2);
        seconds = (EditText) findViewById(R.id.editText);
        action = (Button) findViewById(R.id.button);
        flags = 0;
        toCount = 0;

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(flags){
                    case 0:
                        toCount = Integer.parseInt(seconds.getText().toString());
                        if(toCount <= 0)
                            break;

                        t = (Thread) new Thread(){
                            @Override
                            public void run(){
                                for(int i = toCount; i >= 0; i--){
                                    while(flags != 1){
                                        try {
                                            Thread.sleep(10);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Message msg = timerHandler.obtainMessage();
                                    msg.what = i;
                                    timerHandler.sendMessage(msg);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        };

                        action.setText(R.string.pause);
                        flags = 1;

                        t.start();
                        break;
                    case 1:
                        action.setText(R.string.resume);
                        flags = 2;
                        break;
                    case 2:
                        action.setText(R.string.pause);
                        flags = 1;
                        break;
                }
            }
        });
    }

    // Handler that will received and process messages in the UI thread
    Handler timerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            int third = toCount/3;
            // Retrieve the message and update the textview
            countdown.setText(String.valueOf(message.what));

            if(message.what <= third){
                countdown.setTextColor(Color.RED);
            }


            if(message.what == 0){
                flags = 0;
                action.setText(R.string.start);
                seconds.setText(R.string.default_num);

                Toast.makeText(getBaseContext(), R.string.countdown_complete, Toast.LENGTH_SHORT).show();

            }

            return false;
        }
    });

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
}
