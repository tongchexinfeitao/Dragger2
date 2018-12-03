package com.ali.dragger2demo;

import dagger.Component;

/**
 * Created by mumu on 2018/12/3.
 */

@Component(modules = {MakeCarModule.class} ,dependencies = {AppComponent.class})   //使用componengt修饰的接口， 可以看做是连接需要依赖和提供依赖的中间件
public interface MakeCarComponent {
    void inject(MainActivity mainActivity);    //声明方要注入的方向

}
