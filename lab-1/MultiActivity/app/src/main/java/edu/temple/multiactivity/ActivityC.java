package edu.temple.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityC extends Activity {

    Button openActivityBButton;
    Button openActivityCButton;
    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        openActivityBButton = (Button) findViewById(R.id.button);
        openActivityCButton = (Button) findViewById(R.id.button2);
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        Intent receivedIntent = getIntent();
        final ArrayList<String> list = receivedIntent.getStringArrayListExtra(KeyHelper.DATA);

        if (list !=null){
            TextView myText = new TextView(this);
            for (int i = 0; i < list.size(); i++) {
                myText.append(list.get(i));
                myText.append("\n");
            }
            messageTextView.setText(myText.getText());
        }
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent launchActivityIntent = new Intent(ActivityC.this, ActivityB.class);

                String dataString = "then was opened by Activity C";

                list.add(dataString);
                launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, list);

                startActivity(launchActivityIntent);
            }
        };
        openActivityBButton.setOnClickListener(ocl);

        View.OnClickListener ocl2 = new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent launchActivityIntent = new Intent(ActivityC.this, ActivityB.class);

                String dataString = "then was opened by Activity C";

                list.add(dataString);
                launchActivityIntent.putStringArrayListExtra(KeyHelper.DATA, list);

                startActivity(launchActivityIntent);
            }
        };
        openActivityCButton.setOnClickListener(ocl2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_c, menu);
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
