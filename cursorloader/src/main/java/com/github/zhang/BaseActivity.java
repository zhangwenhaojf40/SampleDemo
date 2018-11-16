package com.github.zhang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    int contacts = 0;
    //联系人权限
     String[] PERMISSIONS_CONTACTS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    //读写权限
    String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public void requestPermiss(int type) {
        String[] perimess = type == contacts ? PERMISSIONS_CONTACTS : PERMISSIONS_STORAGE;
        //是否有权限
        String  have = contacts == type? Manifest.permission.WRITE_CONTACTS:Manifest.permission.READ_EXTERNAL_STORAGE;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,have ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, perimess, 666);
            } else {
                initData();
            }
        } else {
            initData();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 666) {
            int have=0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    have++;
                }
            }
            if (have == grantResults.length) {
                initData();
            } else {
                Toast.makeText(this, "请开启必要权限", Toast.LENGTH_SHORT).show();
            }



        }
    }
    protected abstract void initData();
}
