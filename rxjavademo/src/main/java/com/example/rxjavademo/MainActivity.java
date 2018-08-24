package com.example.rxjavademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_one:
                intent.setClass(this, BaseUseActivity.class);
                break;
            case R.id.btn_two:
                intent.setClass(this, ImageViewActivity.class);
                break;
            case R.id.btn_three:
                intent.setClass(this, MergeTaskActivity.class);
                break;
            case R.id.btn_four:
                intent.setClass(this, FilterActivity.class);
                break;
            case R.id.btn_five:
                intent.setClass(this, TakeActivity.class);
                break;
            case R.id.btn_six:
                intent.setClass(this, IntervalActivity.class);
                break;

        }
        startActivity(intent);
    }
}
