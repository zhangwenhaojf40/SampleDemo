package com.github.zhang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(ContactsActivity.class);
                break;
            case R.id.btn2:
                startActivity(PictureActivity.class);
                break;
        }
    }

    public void startActivity(Class<? extends AppCompatActivity> clz) {
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }
}
