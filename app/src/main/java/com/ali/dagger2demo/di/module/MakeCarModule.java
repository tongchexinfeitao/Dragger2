package com.ali.dagger2demo.di.module;

import com.ali.dagger2demo.mvp.model.bean.Car;
import com.ali.dagger2demo.mvp.model.bean.Engine;
import com.ali.dagger2demo.di.qualifier.EngineL;
import com.ali.dagger2demo.di.qualifier.EngineT;
import com.ali.dagger2demo.di.scope.PerMainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mumu on 2018/12/3.
 */

@Module    //使用module修饰的类，表明这个类是专门用来提供  依赖的；  提供依赖的地方
public class MakeCarModule {

    @Provides
    @PerMainActivity
    Car provideCar(@EngineT Engine engine) {
        return new Car(engine);
    }


    @Provides
    @EngineL
    Engine provideEngine() {
        return new Engine("1.6L");
    }


    @Provides
    @EngineT
    Engine provideEngine2() {
        return new Engine("1.5T");
    }
}
