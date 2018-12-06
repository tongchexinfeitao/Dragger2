package com.ali.dagger2demo.di.component;

import com.ali.dagger2demo.mvp.view.activity.ThirdActivity;

import dagger.Subcomponent;

/**
 * Created by mumu on 2018/12/6.
 */

@Subcomponent()
public interface MySubSubComponent {
    void inject(ThirdActivity thirdActivity);

    @Subcomponent.Builder
    interface Builder {
        MySubSubComponent build();
    }
}
