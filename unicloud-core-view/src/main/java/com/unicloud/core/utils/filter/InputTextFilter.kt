package com.unicloud.core.utils.filter

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.widget.Toast
import java.util.regex.Pattern

/**
 * 质检单输入
 */
class InputTextFilter : InputFilter {
    private var mCtx: Context
    private var mPattern: String
    private var mInvalid: (() -> Unit?)? = null

    constructor(
        ctx: Context,
        invalid: () -> Unit
    ) {
        mCtx = ctx
        mPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5-^φΦ()（）*×./+℃#<>%‰≤≥±]*$"
        mInvalid = invalid
    }

    constructor(
        ctx: Context,
        pattern: String,
        invalid: () -> Unit
    ) {
        mCtx = ctx
        mPattern = pattern
        mInvalid = invalid
    }

    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence {
        val pattern = Pattern.compile(
            mPattern,
            Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
        )
        val emojiMatcher = pattern.matcher(source)
        if (!emojiMatcher.find()) {
            if (mInvalid != null) {
                mInvalid!!.invoke()
            } else {
                Toast.makeText(mCtx, "非法字符！", Toast.LENGTH_SHORT).show()
            }
            return ""
        }
        return ""
    }

    companion object {
        private const val TAG = "InputTextFilter"
    }
}