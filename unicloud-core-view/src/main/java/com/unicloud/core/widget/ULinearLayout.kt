package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper

class ULinearLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), RHelper<UBaseHelper<*>?> {
    private val mHelper: UBaseHelper<ULinearLayout> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }
}