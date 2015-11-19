package edu.temple.lab_8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class StatusActivity extends Activity {
    private ListView updates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        updates = (ListView) findViewById(R.id.updates);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent payload = getIntent();
        List<String> data = payload.getStringArrayListExtra("FILE_UPDATES");
        String[] rev = data.toArray(new String[data.size()]);
        String[] vals = new String[rev.length];

        for(int i = 0; i<rev.length; i++){
            vals[i] = rev[rev.length-i-1];
        }

        if(vals.length != 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, vals);
            updates.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addFile) {
            Intent launchActivityIntent = new Intent(StatusActivity.this, MainActivity.class);
            startActivity(launchActivityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
