package com.ali.dagger2demo.di.module;

import com.ali.dagger2demo.di.qualifier.EngineL;
import com.ali.dagger2demo.di.qualifier.EngineT;
import com.ali.dagger2demo.di.scope.PerMainActivity;
import com.ali.dagger2demo.mvp.model.bean.Car;
import com.ali.dagger2demo.mvp.model.bean.Engine;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mumu on 2018/12/3.
 */

@Module    //使用module修饰的类，表明这个类是专门用来提供  依赖的；  提供依赖的地方
public class MakeCarModule {

    @Provides   //必须使用Providers注解来修饰方法，才能代表这个返回类型可以提供
    @PerMainActivity
    Car provideCar(@EngineT Engine engine) {
        return new Car(engine);
    }


    @Provides
    @EngineL
    Engine provideEngineL() {
        return new Engine("1.6L");
    }


    @Provides
    @EngineT
        //使用限定符注解自定义的注解，用于区分多个方法返回同一个类型对象，具体选择哪个方法
    Engine provideEngineT() {
        return new Engine("1.5T");
    }
}
