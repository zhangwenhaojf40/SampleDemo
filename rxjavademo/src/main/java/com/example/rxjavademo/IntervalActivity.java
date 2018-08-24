package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by ZWH on 2018/8/23 0023.下午 4:38
 * 描述：
 */
public class IntervalActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Button btn = findViewById(R.id.btn);
        compositeDisposable = new CompositeDisposable();

        TextView tv=findViewById(R.id.tv_two);
        btn.setOnClickListener(v -> {
            Observable.interval(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Observer<Long>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onNext(Long value) {
                                    Log.e("aa", Thread.currentThread().getName() );
                                    tv.append(String.valueOf(value));
                                    Log.e("aa", "===============");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            }
                    );
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        compositeDisposable = null;
    }
}
