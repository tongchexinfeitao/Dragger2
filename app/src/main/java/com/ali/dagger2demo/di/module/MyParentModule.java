package com.ali.dagger2demo.di.module;

import com.ali.dagger2demo.di.component.MySubComponent;
import com.ali.dagger2demo.mvp.model.bean.Car;
import com.ali.dagger2demo.mvp.model.bean.Engine;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mumu on 2018/12/3.
 */
//继承关系Module必须显示声明Subcomponent
@Module(subcomponents = MySubComponent.class)
public class MyParentModule {
    @Provides
    Car provideCar() {
        return new Car(new Engine("2.0T"));
    }
}
