package com.ali.dagger2demo.di.component;

import com.ali.dagger2demo.di.module.MySubModule;
import com.ali.dagger2demo.mvp.view.activity.SecondActivity;

import dagger.Subcomponent;

/**
 * Created by mumu on 2018/12/5.
 * <p>
 * 继承关系的子 Component类
 */

//继承关系，子Component必须使用Subcomponent注解
@Subcomponent(modules = MySubModule.class)
public interface MySubComponent {
    void inject(SecondActivity secondActivity);

    //继承关系，子Component必须有自己的Builder类，且必须用Subcomponent修饰
    @Subcomponent.Builder
    interface Builder {
        MySubComponent build();
    }


    MySubSubComponent.Builder mySubSubComponent();
}
