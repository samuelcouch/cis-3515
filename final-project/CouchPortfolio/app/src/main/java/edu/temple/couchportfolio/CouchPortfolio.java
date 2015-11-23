package edu.temple.couchportfolio;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by samcouch on 11/23/15.
 */
public class CouchPortfolio extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
