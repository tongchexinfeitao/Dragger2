package com.ali.dagger2demo.di.module;

import com.ali.dagger2demo.di.component.MySubSubComponent;
import com.ali.dagger2demo.mvp.model.bean.MySubCar;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mumu on 2018/12/6.
 */

@Module(subcomponents = MySubSubComponent.class)
public class MySubModule {
    @Provides
    MySubCar privideMySubCar(){
        return  new MySubCar("我是SubCar 的name xxx");
    }
}
