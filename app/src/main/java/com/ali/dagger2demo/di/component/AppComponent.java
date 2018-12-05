package com.ali.dagger2demo.di.component;

import android.content.Context;

import com.ali.dagger2demo.di.module.AppModule;
import com.ali.dagger2demo.di.scope.PerApp;
import com.ali.dagger2demo.mvp.model.bean.UserBean;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by mumu on 2018/12/3.
 * <p>
 * 依赖关系的 父 Component类
 */

@Component(modules = {AppModule.class})
@PerApp    //必须@PerApp注解修饰，AppComponent，Modue中使用@PerApp修饰的方法的返回对象才能实现单例
public interface AppComponent {
    //依赖关系，在父component中必须显示声明Module中提供的依赖，子类才可以使用
    Context provideAppContext();

    OkHttpClient provideOkHttpClient();

    UserBean provideUserBean();
}
