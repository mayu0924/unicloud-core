package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.github.core.view.widget.helper.TextViewHelper
import com.github.core.view.widget.iface.Helper

class XEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs),
    Helper<TextViewHelper?> {
    private var mHelper: TextViewHelper? = null

    init {
        mHelper = TextViewHelper(
            context,
            this,
            attrs
        )
    }

    override fun getHelper(): TextViewHelper? {
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