package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatRadioButton
import com.unicloud.core.widget.helper.UCheckHelper
import com.unicloud.core.widget.iface.RHelper

class URadioButton @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : AppCompatRadioButton(context, attrs), RHelper<UCheckHelper?> {
    private var mHelper: UCheckHelper? = null

    init {
        mHelper =
            UCheckHelper(context, this, attrs)
    }

    override fun getHelper(): UCheckHelper? {
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