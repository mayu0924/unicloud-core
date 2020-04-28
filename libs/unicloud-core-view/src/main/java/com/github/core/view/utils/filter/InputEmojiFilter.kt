package com.github.core.view.utils.filter

import android.content.Context
import android.text.InputFilter
import android.text.Spanned


class InputEmojiFilter(
    var context: Context?,
    var mInvalid: (() -> Unit?)? = null
) : InputFilter {

    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence? {
//        for (i in start until end) {
//            val type = Character.getType(source[i])
//            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
//                if (mInvalid != null) {
//                    mInvalid!!.invoke()
//                } else {
//                    Toast.makeText(context, "非法字符！", Toast.LENGTH_SHORT).show()
//                }
//                return ""
//            }
//        }
//        return ""
        for (i in start until end) {
            val type: Int = Character.getType(source[i])
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return ""
            }
        }
        return null
    }

    companion object {
        private const val TAG = "InputEmojiFilter"
    }

}