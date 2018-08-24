package com.example.rxjavademo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZWH on 2018/8/23 0023.上午 11:13
 * 描述：
 */
public class MergeTaskActivity extends AppCompatActivity {

    private TextView tv;
    String a ;
    String b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        tv = findViewById(R.id.tv_two);
            findViewById(R.id.btn).setOnClickListener(v -> start());
    }

    private void start() {
        /*
        * 异步处理任务一
        * */
        Observable<Integer> ob1 = Observable.create((ObservableOnSubscribe<Integer>) e -> {
            Thread.sleep(1000);
            e.onNext(1);
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                ;
        ob1.subscribe(s->{
            a = s+"";
            Log.e("accept", "================="+s );
        });
        /*
        * 异步处理任务2
        * */
        Observable<String> ob2 = Observable.create((ObservableOnSubscribe<String>) e -> {
            Thread.sleep(5000);
            e.onNext("2");
            e.onComplete();
        }).subscribeOn(Schedulers.io());
        ob2.subscribe(s -> {
            b = s;
            Log.e("accept", "----------- "+s);
        });
        /*
        * 合并同步
        * */
        Observable.merge(ob1, ob2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
//                        tv.append(value);
                        Log.e("aaa", "onNext: =========================");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        tv.append(a+b);
                        Toast.makeText(MergeTaskActivity.this, "complete", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
