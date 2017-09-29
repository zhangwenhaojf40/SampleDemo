package mypublic.top.appinfomanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<AppInfo> appList;
        public Myadapter(Context context,ArrayList<AppInfo> appList) {
            this.context = context;
            this.appList = appList;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_app, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            AppInfo appInfo = appList.get(position);
            holder.ivIcon.setImageDrawable(appInfo.appIcon);
            holder.tvAppName.setText(appInfo.appName);
            holder.tvAppPageckage.setText(appInfo.packageName);
            holder.tvAppVersion.setText(String.valueOf(appInfo.versionCode));

        }

        @Override
        public int getItemCount() {
            return appList.size();
        }

    }
