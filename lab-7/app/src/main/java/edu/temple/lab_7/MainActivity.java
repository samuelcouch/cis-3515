package edu.temple.lab_7;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private FrameLayout web;
    private ArrayList<Fragment> urls;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = (FrameLayout) findViewById(R.id.web);

        urls = new ArrayList<>();
        count = 0;
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

        switch(id){
            case (R.id.newTab):
                Fragment tab = WebFragment.newInstance();
                urls.add(tab);
                count++;
                loadTab(R.id.web, tab);
                return true;
            case (R.id.prev):
                if(count > 1){
                    count--;
                    loadTab(R.id.web, urls.get(count-1));
                }
                return true;
            case (R.id.next):
                if(count < urls.size()){
                    loadTab(R.id.web, urls.get(count));
                    count++;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadTab(int frame, Fragment frag){
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();

        fragTran.replace(frame, frag);

        fragTran.commit();
        fragMan.executePendingTransactions();
    }
}
