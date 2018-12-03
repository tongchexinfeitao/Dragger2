package com.ali.dragger2demo;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 */

@Module
public class AppModule {
    UserBean userBean;
    App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    Context provideAppContext() {
        return app;
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    UserBean provideUserBean() {
        String name = app.getSharedPreferences("user_sp", Context.MODE_PRIVATE)
                .getString("name", null);
        int userId = app.getSharedPreferences("user_sp", Context.MODE_PRIVATE)
                .getInt("userId", -1);
        userBean = new UserBean(name, userId);
        return userBean;
    }


}
