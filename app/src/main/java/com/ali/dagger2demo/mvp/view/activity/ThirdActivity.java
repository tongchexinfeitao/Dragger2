package com.ali.dagger2demo.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ali.dagger2demo.R;
import com.ali.dagger2demo.app.App;
import com.ali.dagger2demo.di.component.DaggerMyParentComponnet;
import com.ali.dagger2demo.di.component.MySubSubComponent;
import com.ali.dagger2demo.di.module.MyParentModule;
import com.ali.dagger2demo.mvp.model.bean.Car;
import com.ali.dagger2demo.mvp.model.bean.MySubCar;

import javax.inject.Inject;

public class ThirdActivity extends AppCompatActivity {

    @Inject
    MySubCar mySubCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        MySubSubComponent mySubSubComponent = DaggerMyParentComponnet.builder()
                .appComponent(App.getAppComponent())
                .myParentModule(new MyParentModule())
                .build()
                .subComponent()
                .build()
                .mySubSubComponent()
                .build();
        mySubSubComponent.inject(this);
        Toast.makeText(this,"car被赋值" + mySubCar.name(),Toast.LENGTH_LONG).show();
    }
}
