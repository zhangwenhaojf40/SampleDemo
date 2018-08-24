package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by ZWH on 2018/8/22 0022.下午 4:30
 * 描述：简单循环
 */
public class BaseUseActivity extends AppCompatActivity implements View.OnClickListener {
    String arr[] = {"1", "2", "3", "4", "5"};
    private TextView tvTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        findViewById(R.id.btn).setOnClickListener(this);
        tvTwo = findViewById(R.id.tv_two);
    }

    @Override
    public void onClick(View v) {

        Observable.fromArray(arr)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvTwo.append("\n"+s);
                    }
                });
    }
}
