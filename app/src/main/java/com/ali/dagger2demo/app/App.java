package com.ali.dagger2demo.app;

import android.app.Application;

import com.ali.dagger2demo.di.component.AppComponent;
import com.ali.dagger2demo.di.component.DaggerAppComponent;
import com.ali.dagger2demo.di.module.AppModule;

/**
 * Created by mumu on 2018/12/3.
 */


public class App extends Application {


    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    //提供一个全局唯一的AppComponent给外部使用
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}