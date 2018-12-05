package com.ali.dagger2demo.di.module;

import android.content.Context;

import com.ali.dagger2demo.app.App;
import com.ali.dagger2demo.di.component.MySubComponent;
import com.ali.dagger2demo.mvp.model.bean.UserBean;
import com.ali.dagger2demo.di.scope.PerApp;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 */

@Module(subcomponents = MySubComponent.class) //继承关系Module必须显示声明Subcomponent
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
    @PerApp   //声明这是全局唯一的
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @PerApp
    UserBean provideUserBean() {
        String name = app.getSharedPreferences("user_sp", Context.MODE_PRIVATE)
                .getString("name", null);
        int userId = app.getSharedPreferences("user_sp", Context.MODE_PRIVATE)
                .getInt("userId", -1);
        userBean = new UserBean(name, userId);
        return userBean;
    }
}
