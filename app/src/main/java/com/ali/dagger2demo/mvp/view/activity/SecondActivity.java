package com.ali.dagger2demo.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ali.dagger2demo.R;
import com.ali.dagger2demo.app.App;
import com.ali.dagger2demo.di.component.AppComponent;
import com.ali.dagger2demo.di.component.DaggerMyParentComponnet;
import com.ali.dagger2demo.di.component.MyParentComponnet;
import com.ali.dagger2demo.di.component.MySubComponent;
import com.ali.dagger2demo.di.module.MyParentModule;
import com.ali.dagger2demo.mvp.model.bean.Car;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/***
 * 继承关系的需要注入的类
 */

public class SecondActivity extends AppCompatActivity {

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //继承关系，通过父Componnet来拿子Componnet
        MyParentComponnet parentComponnet = DaggerMyParentComponnet
                .builder()
                .myParentModule(new MyParentModule())
                .appComponent(App.getAppComponent())
                .build();
        MySubComponent mySubComponent = parentComponnet
                .subComponent()
                .build();
        mySubComponent.inject(this);

        Toast.makeText(this, okHttpClient.toString(), Toast.LENGTH_LONG).show();


        Toast.makeText(this, "car被注入" +car.toString() , Toast.LENGTH_LONG).show();


    }

    public void startThirdActivity(View view) {
        startActivity(new Intent(this, ThirdActivity.class));

    }
}
