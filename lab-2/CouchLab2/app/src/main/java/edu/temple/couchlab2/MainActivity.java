package edu.temple.couchlab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.GridLayout;

public class MainActivity extends Activity {
    Spinner spinnerColor;
    GridLayout colorPreview;
    Button goToColor;

    private ArrayAdapter<String> adapter;

    private static final String[] colors = {
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
        colorPreview = (GridLayout)findViewById(R.id.colorPreview);
        goToColor = (Button)findViewById(R.id.gotoColor);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapter);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorPreview.setBackgroundColor(android.graphics.Color.parseColor(spinnerColor.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        goToColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorIntent = new Intent(MainActivity.this, backgroundActivity.class);

                colorIntent.putExtra("COLOR_FROM_SPINNER", spinnerColor.getSelectedItem().toString());
                startActivity(colorIntent);
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
