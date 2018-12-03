package com.ali.dragger2demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //告诉dagger我需要注入一个car；  使用依赖的地方

    @Inject
    Car car;
    @Inject
    Context context;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注入依赖
        DaggerMakeCarComponent.builder()
                .appComponent(App.getAppComponent())
                .makeCarModule(new MakeCarModule())
                .build()
                .inject(this);

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
    }
}
