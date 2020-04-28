package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatRadioButton
import com.github.core.view.widget.helper.CheckHelper
import com.github.core.view.widget.iface.Helper

class XRadioButton @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : AppCompatRadioButton(context, attrs),
    Helper<CheckHelper?> {
    private var mHelper: CheckHelper? = null

    init {
        mHelper =
            CheckHelper(context, this, attrs)
    }

    override fun getHelper(): CheckHelper? {
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

    override fun setChecked(checked: Boolean) {
        mHelper?.setChecked(checked)
        super.setChecked(checked)
    }

}