package com.unicloud.core.widget.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.unicloud.core.view.R;
import com.unicloud.core.widget.UToolbar;


public class UToolbarHelper extends UBaseHelper<UToolbar> {

    // Text
    public boolean mIsCenterTitle;

    public UToolbarHelper(Context context, UToolbar view, AttributeSet attrs) {
        super(context, view, attrs);
        initAttributeSet(context, attrs);
    }

    /**
     * 初始化控件属性
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UToolbar);
        //text
        mIsCenterTitle = a.getBoolean(R.styleable.UToolbar_centerTitle, false);

        a.recycle();
    }
}