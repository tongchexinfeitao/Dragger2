package com.ali.dragger2demo;

import android.content.Context;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    //在父component中显示声明，可让子类使用的依赖
    Context provideAppContext();
    OkHttpClient provideOkHttpClient();
    UserBean provideUserBean();
}
