package com.unicloud.core.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.unicloud.core.view.R
import com.unicloud.core.widget.round.RoundDrawable

class UImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {
    /************************
     * Corner
     */
    //圆角
    var corner = -1f
        private set
    var cornerTopLeft = 0f
        private set
    var cornerTopRight = 0f
        private set
    var cornerBottomLeft = 0f
        private set
    var cornerBottomRight = 0f
        private set
    /************************
     * Border
     */
    //边框
    var borderWidth = 0f
        private set
    var borderColor = Color.BLACK
        private set
    //是否圆形
    private var mIsCircle = false
    private var mDrawable: Drawable? = null
    private var mScaleType: ScaleType? = null
    private var mResource = 0
    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private fun initAttributeSet(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.UImageView)
        mIsCircle = a.getBoolean(R.styleable.UImageView_is_circle, false)
        corner = a.getDimensionPixelSize(R.styleable.UImageView_corner_radius, -1).toFloat()
        cornerTopLeft =
            a.getDimensionPixelSize(R.styleable.UImageView_corner_radius_top_left, 0).toFloat()
        cornerTopRight =
            a.getDimensionPixelSize(R.styleable.UImageView_corner_radius_top_right, 0).toFloat()
        cornerBottomLeft =
            a.getDimensionPixelSize(R.styleable.UImageView_corner_radius_bottom_left, 0).toFloat()
        cornerBottomRight =
            a.getDimensionPixelSize(R.styleable.UImageView_corner_radius_bottom_right, 0).toFloat()
        borderWidth = a.getDimensionPixelSize(R.styleable.UImageView_border_width, 0).toFloat()
        borderColor = a.getColor(R.styleable.UImageView_border_color, Color.BLACK)
        a.recycle()
        updateDrawableAttrs()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun setScaleType(scaleType: ScaleType) {
        super.setScaleType(scaleType)
        if (mScaleType != scaleType) {
            mScaleType = scaleType
            updateDrawableAttrs()
            invalidate()
        }
    }

    override fun setImageBitmap(bm: Bitmap) {
        mResource = 0
        mDrawable = RoundDrawable.fromBitmap(bm)
        updateDrawableAttrs()
        super.setImageDrawable(mDrawable)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        mResource = 0
        mDrawable = RoundDrawable.fromDrawable(drawable)
        updateDrawableAttrs()
        super.setImageDrawable(mDrawable)
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        if (mResource != resId) {
            mResource = resId
            mDrawable = resolveResource()
            updateDrawableAttrs()
            super.setImageDrawable(mDrawable)
        }
    }

    private fun resolveResource(): Drawable? {
        val resources = resources ?: return null
        var d: Drawable? = null
        if (mResource != 0) {
            try {
                d = resources.getDrawable(mResource)
            } catch (e: Exception) {
                mResource = 0
            }
        }
        return RoundDrawable.fromDrawable(d)
    }

    private fun updateDrawableAttrs() {
        updateAttrs(mDrawable, mScaleType)
    }

    private fun updateAttrs(drawable: Drawable?, scaleType: ScaleType?) {
        if (drawable == null) return
        if (drawable is RoundDrawable) {
            drawable
                .setScaleType(scaleType)
                .setBorderWidth(borderWidth)
                .setBorderColor(borderColor)
                .setCircle(mIsCircle)
                .setConner(
                    corner,
                    cornerTopLeft,
                    cornerTopRight,
                    cornerBottomLeft,
                    cornerBottomRight
                )
        } else if (drawable is LayerDrawable) {
            val ld = drawable
            var i = 0
            val layers = ld.numberOfLayers
            while (i < layers) {
                updateAttrs(ld.getDrawable(i), scaleType)
                i++
            }
        }
    }

    fun isCircle(isCircle: Boolean): UImageView {
        mIsCircle = isCircle
        updateDrawableAttrs()
        return this
    }

    fun setBorderWidth(borderWidth: Int): UImageView {
        this.borderWidth = borderWidth.toFloat()
        updateDrawableAttrs()
        return this
    }

    fun setBorderColor(@ColorInt borderColor: Int): UImageView {
        this.borderColor = borderColor
        updateDrawableAttrs()
        return this
    }

    fun setCorner(corner: Float): UImageView {
        this.corner = corner
        updateDrawableAttrs()
        return this
    }

    fun setCornerTopLeft(cornerTopLeft: Float): UImageView {
        corner = -1f
        this.cornerTopLeft = cornerTopLeft
        updateDrawableAttrs()
        return this
    }

    fun setCornerTopRight(cornerTopRight: Float): UImageView {
        corner = -1f
        this.cornerTopRight = cornerTopRight
        updateDrawableAttrs()
        return this
    }

    fun setCornerBottomLeft(cornerBottomLeft: Float): UImageView {
        corner = -1f
        this.cornerBottomLeft = cornerBottomLeft
        updateDrawableAttrs()
        return this
    }

    fun setCornerBottomRight(cornerBottomRight: Float): UImageView {
        corner = -1f
        this.cornerBottomRight = cornerBottomRight
        updateDrawableAttrs()
        return this
    }

    fun setCorner(
        topLeft: Float,
        topRight: Float,
        bottomRight: Float,
        bottomLeft: Float
    ): UImageView {
        corner = -1f
        cornerTopLeft = topLeft
        cornerTopRight = topRight
        cornerBottomRight = bottomRight
        cornerBottomLeft = bottomLeft
        updateDrawableAttrs()
        return this
    }

    init {
        initAttributeSet(attrs)
    }
}