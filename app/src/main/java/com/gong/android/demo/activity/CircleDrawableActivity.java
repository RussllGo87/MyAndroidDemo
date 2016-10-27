package com.gong.android.demo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gong.android.demo.R;
import com.gong.android.ui.view.CircleTextView;
import com.gong.android.ui.view.DateSidePicker;
import com.gong.android.ui.view.VerticalTextView;

import java.util.Date;

public class CircleDrawableActivity extends AppCompatActivity {

    DateSidePicker picker_side_date;

    CircleTextView mSpeedView, mTimeView;
    Button btn_circle_view_animate;
    Button btn_circle_view_animation_set;

    ObjectAnimator speedViewAnimator, timeViewAnimator;
    AnimatorSet animatorSet;

    VerticalTextView mDecelerationView, mAscentView, mBrakeView, mTurnView;
    Button btn_vertical_view_animate;
    Button btn_vertical_view_animation_set;

    ObjectAnimator descentViewAnimator, mAscentViewAnimator, mBrakeViewAnimator, mTurnViewAnimator;
    AnimatorSet mVerAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_drawable);

        picker_side_date = (DateSidePicker) findViewById(R.id.picker_side_date);
        picker_side_date.setOnDatePickListener(new DateSidePicker.OnDatePickListener() {
            @Override
            public void onPreClick(Date date) {
                animateCircleView(new int[]{120, 180}, 1500);
                animateVerticalView(new int[]{30, 50, 20, 40}, 1500);
            }

            @Override
            public void onNextClick(Date date) {
                animateCircleView(new int[]{80, 150}, 1500);
                animateVerticalView(new int[]{30, 25, 60, 15}, 1500);
            }
        });

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
                animateCircleView(mSpeedView, 120, 1500);
            }
        });
        btn_vertical_view_animate = (Button) findViewById(R.id.btn_vertical_view_animate);
        btn_vertical_view_animate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateVerticalView(mDecelerationView, 30, 1500);
                mDecelerationView.setSelected(true);
            }
        });
        btn_circle_view_animation_set = (Button) findViewById(R.id.btn_circle_view_animation_set);
        btn_circle_view_animation_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCircleView(new int[]{100, 90}, 1500);
            }
        });
        btn_vertical_view_animation_set = (Button) findViewById(R.id.btn_vertical_view_animation_set);
        btn_vertical_view_animation_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateVerticalView(new int[]{50, 80, 40, 60}, 1500);
            }
        });

        // 添加注释
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

        speedViewAnimator = ObjectAnimator.ofInt(mSpeedView, "progress", 0, progress[0]);
        speedViewAnimator.setDuration(duration);

        timeViewAnimator = ObjectAnimator.ofInt(mTimeView, "progress", 0, progress[1]);
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
