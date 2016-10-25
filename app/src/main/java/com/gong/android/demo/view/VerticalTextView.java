package com.gong.android.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gong.android.demo.R;

/**
 * Created by gongguopei@che-mi.net on 2016/10/24.
 */
public class VerticalTextView extends LinearLayout {

    private Context context;

    private TextView tv_item_title;
    private TextView tv_item_value;

    private String mTitle;
    private int value;
    private boolean selected;

    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VerticalTextView,
                defStyleAttr, 0);

        try {
            mTitle = a.getString(R.styleable.VerticalTextView_ver_title);
        } finally {
            a.recycle();
        }

        initializeViews(context);
    }

    @TargetApi(21)
    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VerticalTextView,
                defStyleAttr, 0);

        try {
            mTitle = a.getString(R.styleable.VerticalTextView_ver_title);
        } finally {
            a.recycle();
        }

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
        inflater.inflate(R.layout.view_vertical_text, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tv_item_title = (TextView) findViewById(R.id.tv_item_title);
        if(!TextUtils.isEmpty(mTitle)) {
            tv_item_title.setText(mTitle);
        }
        tv_item_value = (TextView) findViewById(R.id.tv_item_value);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;

        tv_item_value.setSelected(selected);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;

        tv_item_value.setText(String.valueOf(value));
    }
}
