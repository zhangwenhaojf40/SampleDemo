package mypublic.top.appinfomanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvAppName;
        TextView tvAppPageckage;
        TextView tvAppVersion;
        TextView tvAppVersionName;
        ImageView ivIcon;
        public MyViewHolder(View view)
        {
            super(view);
            tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
            tvAppPageckage = (TextView) view.findViewById(R.id.tv_app_pageckage);
            tvAppVersion = (TextView) view.findViewById(R.id.tv_app_version);
            tvAppVersionName = (TextView) view.findViewById(R.id.tv_app_versionName);
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);

        }
    }