package com.github.core.view.widget.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.github.core.view.R;
import com.github.core.view.widget.XToolbar;


public class ToolbarHelper extends BaseHelper<XToolbar> {

    // Text
    public boolean mIsCenterTitle;

    public ToolbarHelper(Context context, XToolbar view, AttributeSet attrs) {
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XToolbar);
        //text
        mIsCenterTitle = a.getBoolean(R.styleable.XToolbar_centerTitle, false);

        a.recycle();
    }
}