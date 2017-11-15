package br.com.aramosdev.testeandroid.core;

import android.app.Application;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class CustomApplication extends Application {

    private static CustomApplication sInstance;

    public static CustomApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
