package com.unicloud.core.view.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.unicloud.core.utils.ColorUtils
import com.unicloud.core.utils.SizeUtils
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

    // -----------------------------------------------

    private var mUseOriginal = false
    private var mIsHole = false

    private var mStrokeWidth = 1f

    private var mRadius = 0f

    private var mTLRadius = 0f
    private var mTRRadius = 0f
    private var mBLRadius = 0f
    private var mBRRadius = 0f

    private var mStartColor = Color.parseColor("#00C7FF")
    private var mEndColor = Color.parseColor("#2295FF")


    /**
     * 禁用shape样式
     */
    private lateinit var disableGradientDrawable: GradientDrawable
    /**
     * 普通shape样式
     */
    private lateinit var normalGradientDrawable: GradientDrawable
    /**
     * 按压shape样式
     */
    private lateinit var pressedGradientDrawable: GradientDrawable
    /**
     * shape样式集合
     */
    private lateinit var stateListDrawable: StateListDrawable

    private var mGradientOrientation = 0

    init {
        gravity = Gravity.CENTER
    }

    private fun initialize(context: Context, attrs: AttributeSet) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.UNIButton)
        mUseOriginal = arr.getBoolean(R.styleable.UNIButton_uni_btn_original, false)
        mIsHole = arr.getBoolean(R.styleable.UNIButton_uni_btn_isHole, false)
        mRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius, -1f)
        mStrokeWidth = arr.getDimension(R.styleable.UNIButton_uni_btn_strokeWidth, 1f)
        mTLRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius_tl, 0f)
        mTRRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius_tr, 0f)
        mBLRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius_bl, 0f)
        mBRRadius = arr.getDimension(R.styleable.UNIButton_uni_btn_corner_radius_br, 0f)
        mStartColor =
            arr.getColor(R.styleable.UNIButton_uni_btn_start_color, Color.parseColor("#00C7FF"))
        mEndColor =
            arr.getColor(R.styleable.UNIButton_uni_btn_end_color, Color.parseColor("#2295FF"))
        mGradientOrientation = arr.getInteger(R.styleable.UNIButton_uni_btn_gradient_orientation, 0)
        arr.recycle()
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        refreshBackGroundDrawable()
    }

    private fun refreshBackGroundDrawable() {
        if (!mUseOriginal) {
            disableGradientDrawable = createDrawable(0.5f)
            normalGradientDrawable = createDrawable(1f)
            pressedGradientDrawable = createDrawable(0.8f)

            background = if (isClickable && isEnabled) {
                // 5.0以上水波纹效果
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    RippleDrawable(
                        ColorStateList.valueOf(Color.parseColor("#FF666666")),
                        normalGradientDrawable,
                        null
                    )
                } else {// 5.0以下变色效果
                    // 注意此处的add顺序，normal必须在最后一个，否则其他状态无效
                    // 设置pressed状态
                    stateListDrawable = StateListDrawable()
                    stateListDrawable.apply {
                        addState(intArrayOf(android.R.attr.state_pressed), pressedGradientDrawable)
                        // 设置normal状态
                        addState(intArrayOf(), normalGradientDrawable)
                    }
                }
            } else {
                disableGradientDrawable
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        refreshBackGroundDrawable()
    }

    fun setColor(startColor: Int, endColor: Int) {
        mStartColor = resources.getColor(startColor)
        mEndColor = resources.getColor(endColor)
        refreshBackGroundDrawable()
    }

    fun setRadius(radius: Float) {
        mRadius = radius
        refreshBackGroundDrawable()
    }

    fun setIsHole(isHole: Boolean) {
        mIsHole = isHole
        refreshBackGroundDrawable()
    }

    private fun createDrawable(alpha: Float): GradientDrawable {
        return GradientDrawable(
            getGradientDrawableOrientation(),
            intArrayOf(
                ColorUtils.getColorWithAlpha(alpha, mStartColor),
                ColorUtils.getColorWithAlpha(alpha, mEndColor)
            )
        ).apply {
            setDither(true)
            shape = GradientDrawable.RECTANGLE//设置形状为矩形
            gradientType = GradientDrawable.LINEAR_GRADIENT
            bounds = Rect(2, 2, width - 2, height - 2)//设置位置大小
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
                setStroke(
                    SizeUtils.dp2px(mStrokeWidth),
                    ColorUtils.getColorWithAlpha(alpha, mEndColor)
                )
            }
        }
    }

    private fun getGradientDrawableOrientation(): GradientDrawable.Orientation {
        return when (mGradientOrientation) {
            0 -> GradientDrawable.Orientation.LEFT_RIGHT
            45 -> GradientDrawable.Orientation.BL_TR
            90 -> GradientDrawable.Orientation.BOTTOM_TOP
            135 -> GradientDrawable.Orientation.BR_TL
            180 -> GradientDrawable.Orientation.RIGHT_LEFT
            225 -> GradientDrawable.Orientation.TR_BL
            270 -> GradientDrawable.Orientation.TOP_BOTTOM
            315 -> GradientDrawable.Orientation.TL_BR
            else -> GradientDrawable.Orientation.LEFT_RIGHT
        }
    }
}