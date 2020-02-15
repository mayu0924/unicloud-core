package com.unicloud.core.mvvm

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils

/**
 * @author mayu
 * @date 2020/2/4
 */
open class BaseDialog : Dialog {
    private var mWidthModulus = 0.8f
    private var mGravity = Gravity.CENTER_HORIZONTAL

    constructor(context: Context?) : super(context!!, R.style.CommonDialog) {}
    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(false)
        window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
    }

    fun setWidthModulus(widthModulus: Float) {
        mWidthModulus = widthModulus
    }

    fun setGravity(gravity: Int) {
        mGravity = mGravity or gravity
    }

    override fun show() {
        super.show()
        val window = window
        if (window != null) {
            val lp = window.attributes
            lp.gravity = mGravity
            if (mWidthModulus != -1f) {
                lp.width =
                    (context.resources.displayMetrics.widthPixels * mWidthModulus).toInt() // 宽设置为屏幕的0.9
            }
            lp.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            getWindow()!!.attributes = lp
        }
    }

    override fun dismiss() {
        val activity = ActivityUtils.getTopActivity()
        if (activity != null && KeyboardUtils.isSoftInputVisible(activity)) {
            KeyboardUtils.hideSoftInput(activity)
        }
        super.dismiss()
    }
}