package com.gong.android.demo.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gong.android.demo.R;
import com.gong.android.demo.view.CircleTextView;

public class CircleDrawableActivity extends AppCompatActivity {

    CircleTextView mCircleTextView;

    ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_drawable);
    }

    private void animateView(int progress, long duration) {

        if(objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.cancel();
        }

        objectAnimator = ObjectAnimator.ofInt(mCircleTextView, "progress", 0, progress);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    private void animateDrawable() {

    }
}
