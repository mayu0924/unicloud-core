package com.github.core.view.widget.shadow

import android.graphics.*
import android.graphics.drawable.Drawable


class ShadowDrawable : Drawable() {
    private var mShadowColor //阴影颜色
            = 0
    var shadowOffset = 0f //阴影半径
    private lateinit var mRoundRadii: FloatArray //矩形圆角半径
    private var mShadowDx = 0 //阴影x偏移

    private var mShadowDy = 0 //阴影y偏移

    private val mPath: Path
    private val mPaint: Paint
    private val mBoundsF: RectF
    fun updateParameter(
        shadowColor: Int,
        shadowRadius: Float,
        shadowDx: Int,
        shadowDy: Int,
        roundRadii: FloatArray
    ) {
        mShadowColor = shadowColor
        mRoundRadii = roundRadii
        shadowOffset = shadowRadius
        mShadowDx = shadowDx
        mShadowDy = shadowDy
        /**
         * 设置阴影
         */
        mPaint.color = mShadowColor
        mPaint.setShadowLayer(shadowOffset, mShadowDx.toFloat(), mShadowDy.toFloat(), mShadowColor)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        if (bounds.right - bounds.left > 0 && bounds.bottom - bounds.top > 0) {
            updateBounds(bounds)
        }
    }

    /**
     * 更新Bounds
     *
     * @param bounds
     */
    private fun updateBounds(bounds: Rect) {
        var bounds: Rect? = bounds
        if (bounds == null) bounds = getBounds()
        /**
         * 算法1 => 存在偏移的方向增加宽度 （效果不是很好）
         */
        /* float left = bounds.left + getShadowOffsetHalf() + offset;
            left = mShadowDx < 0 ? left + Math.abs(mShadowDx) : left;
            float right = bounds.right - getShadowOffsetHalf() - offset;
            right = mShadowDx > 0 ? right - Math.abs(mShadowDx) : right;
            float top = bounds.top + getShadowOffsetHalf() + offset;
            top = mShadowDy < 0 ? top + Math.abs(mShadowDy) : top;
            float bottom = bounds.bottom - getShadowOffsetHalf() - offset;
            bottom = mShadowDy > 0 ? bottom - Math.abs(mShadowDy) : bottom;
            */
        /**
         * 算法2 => 水平/垂直存在偏移，对应方向增加间距
         */
        val left = bounds.left + shadowOffset + Math.abs(mShadowDx)
        val right = bounds.right - shadowOffset - Math.abs(mShadowDx)
        val top = bounds.top + shadowOffset + Math.abs(mShadowDy)
        val bottom = bounds.bottom - shadowOffset - Math.abs(mShadowDy)
        mBoundsF[left, top, right] = bottom
        mPath.reset() //must重置
        mPath.addRoundRect(mBoundsF, mRoundRadii, Path.Direction.CW)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    init {
        mPath = Path()
        mBoundsF = RectF()
        mPaint =
            Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    }
}