package com.ali.dagger2demo.di.component;

import com.ali.dagger2demo.di.module.MakeCarModule;
import com.ali.dagger2demo.di.scope.PerMainActivity;
import com.ali.dagger2demo.mvp.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by mumu on 2018/12/3.
 * <p>
 * 依赖关系的 子Componnet类
 */
//使用componengt修饰的接口， 可以看做是连接需要依赖和提供依赖的中间件
@Component(modules = {MakeCarModule.class}, dependencies = {AppComponent.class})
@PerMainActivity
public interface MakeCarComponent {
    void inject(MainActivity mainActivity);    //必须要声明方要注入的方向

}
