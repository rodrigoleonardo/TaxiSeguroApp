package edu.galileo.android.taxiseguro;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by rodrigo on 11/06/16.
 */
public class TaxiSeguroAndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        //inicializa firebase
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);//capacidad offline
    }
}
