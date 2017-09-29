package mypublic.top.appinfomanager;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AppInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取所用应用集合
        getAppList();
        //初始化recyclerview
        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_app);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Myadapter(this,appList));

    }

    public void getAppList() {
        //用来存储获取的应用信息数据
        appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            AppInfo appInfo = new AppInfo();
            appInfo.packageName = packageInfo.packageName;

            appInfo.appName=packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            appInfo.versionName = packageInfo.versionName;
            appInfo.versionCode = packageInfo.versionCode;
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            appList.add(appInfo);
        }

    }


}
