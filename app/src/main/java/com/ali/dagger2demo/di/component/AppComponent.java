package com.ali.dagger2demo.di.component;

import android.content.Context;

import com.ali.dagger2demo.mvp.model.bean.UserBean;
import com.ali.dagger2demo.di.module.AppModule;
import com.ali.dagger2demo.di.scope.PerApp;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 */

@Component(modules = {AppModule.class})
@PerApp
public interface AppComponent {
    //在父component中显示声明，可让子类使用的依赖
    Context provideAppContext();
    OkHttpClient provideOkHttpClient();
    UserBean provideUserBean();
}
