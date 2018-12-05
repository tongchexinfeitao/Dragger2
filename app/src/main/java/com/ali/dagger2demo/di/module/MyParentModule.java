package com.ali.dagger2demo.di.module;

import com.ali.dagger2demo.di.component.MySubComponent;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 */
//继承关系Module必须显示声明Subcomponent
@Module(subcomponents = MySubComponent.class)
public class MyParentModule {
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .build();
    }
}
