package com.example.rxjavademo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;


/**
 * Created by ZWH on 2018/8/23 0023.下午 2:27
 * 描述：过滤 掉被2整除的
 */
public class FilterActivity extends AppCompatActivity {
    Integer arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String info = "1-10，过滤掉能被2整除的";
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ((TextView)findViewById(R.id.tv_one)).setText(info);

        tv = findViewById(R.id.tv_two);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {

            Observable.fromArray(arr)
                    .filter(integer -> integer%2==0)
                   .subscribe(integer -> {
                    tv.append(String.valueOf(integer));
                   });
        });
    }
}
