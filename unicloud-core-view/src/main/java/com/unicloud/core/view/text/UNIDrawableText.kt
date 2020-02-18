package com.unicloud.core.view.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.unicloud.core.utils.SizeUtils.dp2px
import com.unicloud.core.view.R


/**
 * @author mayu
 * @date 2018/11/29/029
 */
class UNIDrawableText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private var mShapeDrawable: Drawable? = null

    private var mDrawableMode: Int
    private var mDrawableHeight: Int
    private var mDrawableWidth: Int
    private var mDrawable: Drawable?
    private var mLocation: Int
    private var mIsInterceptClick = false


    companion object {
        const val LEFT = 1
        const val TOP = 2
        const val RIGHT = 3
        const val BOTTOM = 4
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.UNIDrawableText
        )
        mDrawableMode = a.getInteger(R.styleable.UNIDrawableText_uni_drawable_mode, 0)
        mDrawableWidth = a.getDimensionPixelSize(R.styleable.UNIDrawableText_uni_drawable_width, 0)
        mDrawableHeight =
            a.getDimensionPixelSize(R.styleable.UNIDrawableText_uni_drawable_height, 0)
        mDrawable = a.getDrawable(R.styleable.UNIDrawableText_uni_drawable_src)
        mLocation = a.getInt(R.styleable.UNIDrawableText_uni_drawable_location, LEFT)
        mIsInterceptClick =
            a.getBoolean(R.styleable.UNIDrawableText_uni_drawable_isIntercept, false)
        a.recycle()
        drawDrawable()
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        drawDrawable()
    }

    /**
     * 绘制Drawable宽高,位置
     */
    private fun drawDrawable() {
        mShapeDrawable = when (mDrawableMode) {
            0 -> {
                BitmapDrawable(
                    resources,
                    getBitmap((mDrawable as BitmapDrawable).bitmap, mDrawableWidth, mDrawableHeight)
                )
            }
            1 -> {
                ShapeDrawable(OvalShape()).apply {
                    paint.style = Paint.Style.FILL
                    paint.color =
                        if (text.toString() != "") currentTextColor else Color.parseColor("#000000")
                    setBounds(0, 0, mDrawableWidth, mDrawableHeight)
                }
            }
            2 -> {
                ShapeDrawable(RectShape()).apply {
                    paint.style = Paint.Style.FILL
                    paint.color =
                        if (text.toString() != "") currentTextColor else Color.parseColor("#000000")
                    setBounds(0, 0, mDrawableWidth, mDrawableHeight)
                }
            }
            else -> null
        }


        if (mShapeDrawable != null) {
            if (mDrawableMode == 0) {
                when (mLocation) {
                    LEFT -> this.setCompoundDrawablesWithIntrinsicBounds(
                        mShapeDrawable, null,
                        null, null
                    )
                    TOP -> this.setCompoundDrawablesWithIntrinsicBounds(
                        null, mShapeDrawable,
                        null, null
                    )
                    RIGHT -> this.setCompoundDrawablesWithIntrinsicBounds(
                        null, null,
                        mShapeDrawable, null
                    )
                    BOTTOM -> this.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, null,
                        mShapeDrawable
                    )
                }
            } else {
                when (mLocation) {
                    LEFT -> this.setCompoundDrawables(
                        mShapeDrawable, null,
                        null, null
                    )
                    TOP -> this.setCompoundDrawables(
                        null, mShapeDrawable,
                        null, null
                    )
                    RIGHT -> this.setCompoundDrawables(
                        null, null,
                        mShapeDrawable, null
                    )
                    BOTTOM -> this.setCompoundDrawables(
                        null, null, null,
                        mShapeDrawable
                    )
                }
            }
        }
    }

    fun setDrawable(
        drawableMode: Int,
        drawable: Drawable?,
        location: Int,
        width: Int,
        height: Int
    ) {
        mDrawableMode = drawableMode
        mDrawable = drawable
        mLocation = location
        mDrawableWidth = width
        mDrawableHeight = height
        drawDrawable()
    }

    var isDownTrue = false
    var isUpTrue = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mIsInterceptClick) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> isDownTrue = isInClickArea(event)
                MotionEvent.ACTION_UP -> {
                    isUpTrue = isInClickArea(event)
                    if (isDownTrue && isUpTrue) {
                        performClick()
                        return false
                    }
                }
            }
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    fun setInterceptClickEnable(isInterceptClick: Boolean) {
        mIsInterceptClick = isInterceptClick
    }

    private fun isInClickArea(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        var isInClickArea = false
        when (mLocation) {
            LEFT -> isInClickArea =
                x < paddingLeft + mDrawableWidth + compoundDrawablePadding + dp2px(
                    30f
                )
            RIGHT -> isInClickArea =
                x < width - paddingRight + mDrawableWidth + compoundDrawablePadding
            TOP -> isInClickArea =
                y < paddingTop + mDrawableHeight + compoundDrawablePadding
            BOTTOM -> isInClickArea =
                y > height - (paddingBottom + mDrawableHeight + compoundDrawablePadding)
        }
        return isInClickArea
    }

    /**
     * 缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    private fun getBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap { // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width.toFloat()
        val scaleHeight = newHeight.toFloat() / height.toFloat()
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }
}