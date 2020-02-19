package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.unicloud.core.widget.helper.UTextViewHelper
import com.unicloud.core.widget.iface.RHelper

class UEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs),
    RHelper<UTextViewHelper?> {
    private var mHelper: UTextViewHelper? = null

    init {
        mHelper = UTextViewHelper(context, this, attrs)
    }

    override fun getHelper(): UTextViewHelper? {
        return mHelper
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mHelper?.setEnabled(enabled)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mHelper?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun setSelected(selected: Boolean) {
        mHelper?.setSelected(selected)
        super.setSelected(selected)
    }

}