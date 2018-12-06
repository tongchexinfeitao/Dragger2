package com.ali.dagger2demo.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ali.dagger2demo.R;
import com.ali.dagger2demo.di.component.DaggerMyParentComponnet;
import com.ali.dagger2demo.di.component.MyParentComponnet;
import com.ali.dagger2demo.di.component.MySubComponent;
import com.ali.dagger2demo.di.module.MyParentModule;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/***
 * 继承关系的需要注入的类
 */

public class SecondActivity extends AppCompatActivity {

    @Inject
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //继承关系，通过父Componnet来拿子Componnet
        MyParentComponnet parentComponnet = DaggerMyParentComponnet
                .builder()
                .myParentModule(new MyParentModule())
                .build();
        MySubComponent mySubComponent = parentComponnet
                .subComponent()
                .build();
        mySubComponent.inject(this);

        Toast.makeText(this, okHttpClient.toString(), Toast.LENGTH_LONG).show();

    }
}
