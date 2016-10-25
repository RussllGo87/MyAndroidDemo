package com.gong.android.demo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gong.android.demo.R;
import com.gong.android.demo.view.CircleTextView;
import com.gong.android.demo.view.VerticalTextView;

import static android.animation.ObjectAnimator.ofInt;

public class CircleDrawableActivity extends AppCompatActivity {

    CircleTextView mSpeedView, mTimeView;

    ObjectAnimator speedViewAnimator, timeViewAnimator;

    AnimatorSet animatorSet, mVerAnimatorSet;

    VerticalTextView mDecelerationView, mAscentView, mBrakeView, mTurnView;
    ObjectAnimator descentViewAnimator, mAscentViewAnimator, mBrakeViewAnimator, mTurnViewAnimator;

    Button btn_circle_view_animate;
    Button btn_vertical_view_animate;
    Button btn_circle_view_animation_set;
    Button btn_vertical_view_animation_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_drawable);

        mSpeedView = (CircleTextView) findViewById(R.id.tv_circle_speed);
        mTimeView = (CircleTextView) findViewById(R.id.tv_circle_time);

        mDecelerationView = (VerticalTextView) findViewById(R.id.ver_tv_descent);
        mAscentView = (VerticalTextView) findViewById(R.id.ver_tv_ascent);
        mBrakeView = (VerticalTextView) findViewById(R.id.ver_tv_brake);
        mTurnView = (VerticalTextView) findViewById(R.id.ver_tv_turn);

        btn_circle_view_animate = (Button) findViewById(R.id.btn_circle_view_animate);
        btn_circle_view_animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCircleView(mSpeedView, 90, 1500);
            }
        });
        btn_vertical_view_animate = (Button) findViewById(R.id.btn_vertical_view_animate);
        btn_vertical_view_animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateVerticalView(mDecelerationView, 40, 1500);
                mDecelerationView.setSelected(true);
            }
        });
        btn_circle_view_animation_set = (Button) findViewById(R.id.btn_circle_view_animation_set);
        btn_circle_view_animation_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCircleView(new int[]{50, 80}, 1500);
            }
        });
        btn_vertical_view_animation_set = (Button) findViewById(R.id.btn_vertical_view_animation_set);
        btn_vertical_view_animation_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateVerticalView(new int[]{50, 80, 40, 60}, 1500);
            }
        });
    }

    private void animateCircleView(CircleTextView view, int progress, long duration) {
        if(view == null) {
            return;
        }

        if(speedViewAnimator != null && speedViewAnimator.isRunning()) {
            speedViewAnimator.cancel();
        }

        speedViewAnimator = ObjectAnimator.ofInt(view, "progress", 0, progress);
        speedViewAnimator.setDuration(duration);
        speedViewAnimator.start();
    }

    private void animateCircleView(int[] progress, long duration) {

        if(progress == null || progress.length < 2) {
            return;
        }

        if(speedViewAnimator != null && speedViewAnimator.isRunning()) {
            speedViewAnimator.cancel();
        }

        if(timeViewAnimator != null && timeViewAnimator.isRunning()) {
            timeViewAnimator.cancel();
        }

        speedViewAnimator = ofInt(mSpeedView, "progress", 0, progress[0]);
        speedViewAnimator.setDuration(duration);

        timeViewAnimator = ofInt(mTimeView, "progress", 0, progress[1]);
        timeViewAnimator.setDuration(duration);

        if(animatorSet != null && animatorSet.isRunning()) {
            animatorSet.cancel();
            animatorSet.start();

        } else {
            animatorSet = new AnimatorSet();
            animatorSet.play(speedViewAnimator).with(timeViewAnimator);
            animatorSet.start();
        }
    }

    private void animateVerticalView(VerticalTextView view, int progress, long duration) {
        if(view == null) {
            return;
        }
        if(descentViewAnimator != null && descentViewAnimator.isRunning()) {
            descentViewAnimator.cancel();
        }

        descentViewAnimator = ObjectAnimator.ofInt(view, "value", 0, progress);
        descentViewAnimator.setDuration(duration);
        descentViewAnimator.start();
    }

    private void animateVerticalView(int[] progress, long duration) {

        if(progress == null || progress.length < 4) {
            return;
        }

        if(descentViewAnimator != null && descentViewAnimator.isRunning()) {
            descentViewAnimator.cancel();
        }

        descentViewAnimator = ObjectAnimator.ofInt(mDecelerationView, "value", 0, progress[0]);
        descentViewAnimator.setDuration(duration);

        mAscentViewAnimator = ObjectAnimator.ofInt(mAscentView, "value", 0, progress[1]);
        mAscentViewAnimator.setDuration(duration);

        mBrakeViewAnimator = ObjectAnimator.ofInt(mBrakeView, "value", 0, progress[2]);
        mBrakeViewAnimator.setDuration(duration);

        mTurnViewAnimator = ObjectAnimator.ofInt(mTurnView, "value", 0, progress[3]);
        mTurnViewAnimator.setDuration(duration);

        if(mVerAnimatorSet != null && mVerAnimatorSet.isRunning()) {
            mVerAnimatorSet.cancel();
            mVerAnimatorSet.start();

        } else {
            mVerAnimatorSet = new AnimatorSet();
            mVerAnimatorSet.play(descentViewAnimator)
                    .with(mAscentViewAnimator)
                    .with(mBrakeViewAnimator)
                    .with(mTurnViewAnimator);
            mVerAnimatorSet.start();
        }
    }
}
