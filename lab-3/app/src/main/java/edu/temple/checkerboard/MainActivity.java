package edu.temple.checkerboard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.tud23264.lab3.R;


public class MainActivity extends Activity implements myFragment1.onClickButtonListener  {

    boolean twoPanes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twoPanes = (findViewById(R.id.fragment_2) != null);
        Fragment my_Fragment1 = new Fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_1, new myFragment1());
        fragmentTransaction.commit();

        if (twoPanes){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_2, new myFragment2());
            fragmentTransaction.commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onNumberSelected(int number){
        Bundle args = new Bundle();
        if (!twoPanes) {
            args.putInt("num", number);
            loadFragment(R.id.fragment_1, new myFragment2(), true, args);
        }
        else {
            myFragment2 gridFragment = (myFragment2)getFragmentManager().findFragmentById(R.id.fragment_2);

            gridFragment.newNumber(number);

        }

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
    private void loadFragment(int paneId, Fragment fragment, boolean placeOnBackstack, Bundle args){
        FragmentManager fm = getFragmentManager();
        fragment.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction()
                .replace(paneId, fragment);
        if (placeOnBackstack)
            ft.addToBackStack(null);
        ft.commit();

        fm.executePendingTransactions();
    }
}
