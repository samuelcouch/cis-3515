package edu.temple.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class ActivityA extends Activity {

    Button openActivityBButton;
    Button openActivityCButton;
    TextView messageTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        openActivityBButton = (Button) findViewById(R.id.button);
        openActivityCButton = (Button) findViewById(R.id.button2);

        messageTextView = (TextView) findViewById(R.id.messageTextView);

        Intent receivedIntent = getIntent();

        final ArrayList<String> list = receivedIntent.getStringArrayListExtra(KeyHelper.DATA);

        if (list != null){
            TextView myText = new TextView(this);
            for (int i = 0; i<list.size();i++){
                myText.append(list.get(i));
                myText.append("\n");
            }
            messageTextView.setText(myText.getText());
        } else {
            messageTextView.setText("Nothing to see here yet. Navigate around!");
        }


        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent launchActivityIntent = new Intent(ActivityA.this, ActivityB.class);

                String dataString = "then was opened by Activity A";
                if (list!= null) {
                    list.add(dataString);
                    launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, list);
                }else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add("Opened by Activity A");
                    launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, newList);
                }startActivity(launchActivityIntent);
            }

        };
        openActivityBButton.setOnClickListener(ocl);

        View.OnClickListener ocl2 = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent launchActivityIntent = new Intent(ActivityA.this, ActivityC.class);

                String dataString = "then was opened by Activity A ";
                if (list!= null) {
                    list.add(dataString);
                    launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, list);
                }else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add("Opened by Activity A");
                    launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, newList);
                }startActivity(launchActivityIntent);
            }

        };
        openActivityCButton.setOnClickListener(ocl2);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
