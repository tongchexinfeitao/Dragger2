package com.ali.dagger2demo.di.component;

import com.ali.dagger2demo.di.module.MyParentModule;

import dagger.Component;

/**
 * Created by mumu on 2018/12/5.
 *
 * 继承关系的父Component
 */
@Component(modules = {MyParentModule.class})
public interface MyParentComponnet {
    //继承关系父Component必须显式的提供生成子Subcomponent.Builder的方法
    //而不需要显示声明Module中提供的依赖
    MySubComponent.Builder subComponent();

}
