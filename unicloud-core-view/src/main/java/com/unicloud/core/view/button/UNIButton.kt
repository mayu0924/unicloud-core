package com.unicloud.core.view.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.unicloud.core.utils.ColorUtils
import com.unicloud.core.view.R


class UNIButton : AppCompatButton {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }

    private var mAlpha = 1f

    // -----------------------------------------------

    private var mIsHole = false

    private var mRadius = 0f

    private var mTLRadius = 0f
    private var mTRRadius = 0f
    private var mBLRadius = 0f
    private var mBRRadius = 0f

    private var mStartColor = Color.parseColor("#00C7FF")
    private var mEndColor = Color.parseColor("#2295FF")

    init {
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setPadding(0, 0, 0, 0)
        gravity = Gravity.CENTER
    }

    private fun initialize(context: Context, attrs: AttributeSet) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.UNIButton)
        mIsHole = arr.getBoolean(R.styleable.UNIButton_uni_btn_isHole, false)
        mRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius, -1f)
        mTLRadius = arr.getDimension(R.styleable.UNIText_uni_text_corner_radius_tl, 0f)
        mTRRadius = arr.getDimension(R.styleable.UNIText_uni_text_corner_radius_tr, 0f)
        mBLRadius = arr.getDimension(R.styleable.UNIText_uni_text_corner_radius_bl, 0f)
        mBRRadius = arr.getDimension(R.styleable.UNIText_uni_text_corner_radius_br, 0f)
        mStartColor =
            arr.getColor(R.styleable.UNIButton_uni_btn_start_color, Color.parseColor("#00C7FF"))
        mEndColor =
            arr.getColor(R.styleable.UNIButton_uni_btn_end_color, Color.parseColor("#2295FF"))
        arr.recycle()
        mAlpha = if (isEnabled) 1f else 0.5f
    }

    fun setBgColor(startColor: Int, endColor: Int) {
        mStartColor = resources.getColor(startColor)
        mEndColor = resources.getColor(endColor)
        background = createDrawable()
    }

    fun setRadius(radius: Float) {
        mRadius = radius
    }

    fun setIsHole(isHole: Boolean) {
        mIsHole = isHole
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mAlpha = if (enabled) 1f else 0.5f
        invalidate()
    }

    private fun createDrawable(): Drawable {
        return GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                ColorUtils.getColorWithAlpha(mAlpha, mStartColor),
                ColorUtils.getColorWithAlpha(mAlpha, mEndColor)
            )
        ).apply {
            setDither(true)
            shape = GradientDrawable.RECTANGLE//设置形状为矩形
            gradientType = GradientDrawable.LINEAR_GRADIENT
            bounds = Rect(0, 0, width, height);//设置位置大小
            if (mRadius != -1f) {
                cornerRadius = mRadius
            } else {
                cornerRadii = floatArrayOf(
                    mTLRadius,
                    mTLRadius,
                    mTRRadius,
                    mTRRadius,
                    mBRRadius,
                    mBRRadius,
                    mBLRadius,
                    mBLRadius
                )
            }
            if (mIsHole) {
                setColor(Color.parseColor("#FFFFFF"))
                setStroke(3, ColorUtils.getColorWithAlpha(mAlpha, mEndColor))
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        background = createDrawable()
    }

    fun update() {
        background = createDrawable()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        background = createDrawable()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mAlpha = 0.8f
                    background = createDrawable()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    mAlpha = 1f
                    background = createDrawable()
                }
            }
        }
        return super.onTouchEvent(event)
    }
}