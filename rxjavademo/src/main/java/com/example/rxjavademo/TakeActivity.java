package com.example.rxjavademo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


/**
 * Created by ZWH on 2018/8/23 0023.下午 3:18
 * 描述：
 */
public class TakeActivity extends AppCompatActivity {
    String info = "数组[1,2,3,4,5,6,7,8,9,10]第三个和第四个奇数";
    Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        TextView tv = findViewById(R.id.tv_two);
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            Observable.fromArray(arr)
                    .filter(integer -> integer % 2 != 0)
                    .take(3)

                    .subscribe(integer -> {
                        tv.append(String.valueOf(integer));
                    });
        });
    }
}
