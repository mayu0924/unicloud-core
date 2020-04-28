package com.github.core.view.utils.filter

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast

class InputMinMaxFilter : InputFilter {
    private var mCtx: Context
    private var min: Double = 0.toDouble()
    private var max: Double = 0.toDouble()

    private var mEditText: EditText

    private var mToast: ((Double, Double) -> Unit?)? = null

    constructor(
        context: Context,
        editText: EditText,
        min: Double,
        max: Double,
        toast: (min: Double, max: Double) -> Unit
    ) {
        mCtx = context
        mEditText = editText
        this.min = min
        this.max = max
        this.mToast = toast
        setOnFocusChangeListener()
    }

    constructor(context: Context, editText: EditText, min: String, max: String) {
        mCtx = context
        mEditText = editText
        this.min = java.lang.Double.parseDouble(min)
        this.max = java.lang.Double.parseDouble(max)
        setOnFocusChangeListener()
    }

    private fun setOnFocusChangeListener() {
        mEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!TextUtils.isEmpty(mEditText.text.toString())) {
                val input = mEditText.text.toString().toDouble()
                if (!isInRange(min, max, input)) {
                    if (input < min)
                        mEditText.setText(min.toString())
                    else
                        mEditText.setText(max.toString())
                    mToast?.invoke(min, max)
                }
            }
        }
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        //以点开始的时候，自动在前面添加0
        if ("." == source.toString() && dstart == 0) {
            return "0."
        }
        try {
            val input = java.lang.Double.parseDouble(dest.toString() + source.toString())
            if (isInRange2(min, max, input)) {
                return null
            } else {
                if (mToast != null) {
                    mToast!!.invoke(min, max)
                } else {
                    Toast.makeText(mCtx, "输入值范围[${min},${max}]", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (ignored: Exception) {
        }
        return ""
    }

    private fun isInRange2(a: Double, b: Double, c: Double): Boolean {
        return if (b > a) c <= b else c <= a
    }

    private fun isInRange(a: Double, b: Double, c: Double): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}