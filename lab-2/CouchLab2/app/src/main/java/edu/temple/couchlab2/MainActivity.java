package edu.temple.couchlab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {
    Spinner spinnerColor;

    private ArrayAdapter<String> adapter;

    private static final String[] colors = {
            "SELECT A COLOR",
            "red",
            "blue",
            "green",
            "black",
            "white",
            "gray",
            "cyan",
            "magenta",
            "yellow",
            "lightgray",
            "darkgray",
            "grey",
            "lightgrey",
            "darkgrey",
            "aqua",
            "fuchsia",
            "lime",
            "maroon",
            "navy",
            "olive",
            "purple",
            "silver",
            "teal"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerColor = (Spinner)findViewById(R.id.colorspinner);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapter);

        spinnerColor.setSelection(0);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String picked = spinnerColor.getSelectedItem().toString();

                if (!picked.equals("SELECT A COLOR")){
                    Intent colorIntent = new Intent(MainActivity.this, backgroundActivity.class);

                    colorIntent.putExtra("COLOR_FROM_SPINNER", picked);
                    startActivity(colorIntent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
}
