package com.ali.dagger2demo.mvp.model.bean;

/**
 * Created by mumu on 2018/12/3.
 */

public class Engine {
    public String name;

     //可以修饰构造器，这样dagger就会自动调用构造器去构造该对象，
              // 但是module中的优先级要高于被@Inject修改的构造器
    public Engine(String name) {
        this.name = name;
    }
}
