package com.example.soichiro.simpledaycome;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by soichiro on 2018/02/18.
 */

public class MySchedulerApplication extends Application {
    @Override
    public  void  onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
