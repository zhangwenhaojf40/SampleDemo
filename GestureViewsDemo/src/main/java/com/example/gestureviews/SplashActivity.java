package com.example.gestureviews;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class SplashActivity extends BaseActivity {

    private GestureImageView gestureView;
    private ViewsTransitionAnimator animator;

    @Override
    protected void initData() {
        final ImageView iv = findViewById(R.id.iv);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        gestureView = (GestureImageView) findViewById(R.id.gesture);
        animator = GestureTransitions.from(iv).into(gestureView);
        animator.addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(float position, boolean isLeaving) {
                applyImageAnimationState(position,isLeaving);
            }
        });
      /*  gestureView.getPositionAnimator().setDuration(1000);
        gestureView.getController().getSettings()
                .setMaxZoom(2f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER)

        ;*/
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gestureView.getDrawable() == null) {
                    gestureView.setImageDrawable(iv.getDrawable());
                }

                gestureView.getController().resetState();

                animator.enterSingle(true);
            }
        });

        ArrayList<String> activities = getActivities(this);
        String b = "";
        for (String a : activities) {
            b = b + a;
        }
        tv1.setText(b);
//        tv2.setText("---"+packageName);
    }

    @Override
    protected int initView() {

        return R.layout.activity_splash;
    }
    // 获取应用程序下所有Activity
    public static ArrayList<String> getActivities(Context ctx) {
        ArrayList<String> result = new ArrayList<String>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.setPackage(ctx.getPackageName());
        for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(intent, 0)) {
            result.add(info.activityInfo.name);
        }
        return result;
    }

    private void applyImageAnimationState(float position, boolean isLeaving) {
        /*fullBackground.setAlpha(position);
        fullBackground.setVisibility(position == 0f && isLeaving ? View.INVISIBLE : View.VISIBLE);*/
        gestureView.setVisibility(position == 0f && isLeaving ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        // We should leave full image mode instead of closing the screen
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onBackPressed();
        }
    }
}
