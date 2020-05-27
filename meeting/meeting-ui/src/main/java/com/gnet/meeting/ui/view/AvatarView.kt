package com.gnet.meeting.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * <pre>
 *     author : mayu
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/5/11 15:33
 *     desc   : 头像
 *     version: 1.0
 * </pre>
 */
class AvatarView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }
}