package com.ali.dragger2demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mumu on 2018/12/3.
 */

@Module    //使用module修饰的类，表明这个类是专门用来提供  依赖的；  提供依赖的地方
public class MakeCarModule {

    @Provides
    Car provideCar(Engine engine) {
        return new Car(engine);
    }


    @Provides
    Engine provideEngine() {
        return new Engine("1.6L");
    }


    @Provides
    Engine provideEngine2() {
        return new Engine("1.5T");
    }
}
