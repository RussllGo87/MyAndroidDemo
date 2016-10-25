package com.gong.android.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gong.android.demo.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gongguopei@che-mi.net on 2016/10/25.
 */

public class DateSidePicker extends LinearLayout {

    private ImageView iv_date_pre;
    private TextView tv_date;
    private ImageView iv_date_next;

    Date date;

    interface OnDatePikerListener {
        void onPreClick(Date date);
        void onNextClick(Date date);
    }

    OnDatePikerListener mListener;

    public DateSidePicker(Context context) {
        this(context, null);
    }

    public DateSidePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateSidePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setDate(new Date());
        initializeViews(context);
    }

    @TargetApi(21)
    public DateSidePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setDate(new Date());
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.picker_side_date, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        iv_date_pre = (ImageView) findViewById(R.id.iv_date_pre);
        iv_date_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preDay();
                if(mListener != null) {
                    mListener.onPreClick(getDate());
                }
            }
        });
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(showDate(date));
        iv_date_next = (ImageView) findViewById(R.id.iv_date_next);
        iv_date_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDay();
                if(mListener != null) {
                    mListener.onNextClick(getDate());
                }
            }
        });
        iv_date_next.setVisibility(View.INVISIBLE);
    }

    private void preDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        setDate(calendar.getTime());
        tv_date.setText(showDate(date));

        iv_date_next.setVisibility(View.VISIBLE);
    }

    private void nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        setDate(calendar.getTime());
        tv_date.setText(showDate(date));

        Date current = new Date();
        calendar.setTime(current);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        current = calendar.getTime();

        if(!isTheSameDate(getDate(), current)) {
            iv_date_next.setVisibility(View.VISIBLE);
        } else {
            iv_date_next.setVisibility(View.INVISIBLE);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        this.date = calendar.getTime();
    }

    private String showDate(Date mDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        StringBuilder stringBuilder = new StringBuilder();
        if(month < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(month);
        stringBuilder.append('-');
        if(day < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(day);

        return stringBuilder.toString();
    }

    private String showDateTime(Date mDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        StringBuilder stringBuilder = new StringBuilder();
        if(month < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(month);
        stringBuilder.append('-');
        if(day < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(day);
        stringBuilder.append(" ");

        if(hour < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(hour);
        stringBuilder.append(":");

        if(min < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(min);
        stringBuilder.append(":");

        if(second < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(second);

        return stringBuilder.toString();
    }

    private boolean isTheSameDate(Date day, Date mDay) {
        if(day == null || mDay == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(mDay);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if(year == mYear && month == mMonth && dayOfMonth == mDayOfMonth) {
            return true;
        } else {
            return false;
        }
    }

}
