package com.ali.dagger2demo.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ali.dagger2demo.R;
import com.ali.dagger2demo.app.App;
import com.ali.dagger2demo.di.component.DaggerMakeCarComponent;
import com.ali.dagger2demo.di.component.MakeCarComponent;
import com.ali.dagger2demo.di.module.MakeCarModule;
import com.ali.dagger2demo.mvp.model.bean.Car;
import com.ali.dagger2demo.mvp.model.bean.UserBean;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/***
 * 依赖关系的需要注入的类
 */
public class MainActivity extends AppCompatActivity {
    //告诉dagger我需要注入一个car；  使用依赖的地方
    @Inject
    Car car;
    @Inject
    Car car1;
    @Inject
    Context context;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    UserBean userBean;
    private MakeCarComponent makeCarComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注入依赖
        makeCarComponent = DaggerMakeCarComponent.builder()
                .appComponent(App.getAppComponent())
                .makeCarModule(new MakeCarModule())
                .build();
        makeCarComponent.inject(this);

        Toast.makeText(context, car.engine.name, Toast.LENGTH_SHORT).show();

        Call call = okHttpClient.newCall(new Request.Builder().url("http://www.baidu.com/").build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "联网成功", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        if (car == car1) {
            Toast.makeText(this, "car是同一个对象", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "car不是一个对象", Toast.LENGTH_LONG).show();
        }
    }

    public void startSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
