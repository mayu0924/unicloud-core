package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper

class URelativeLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), RHelper<UBaseHelper<*>?> {
    private val mHelper: UBaseHelper<URelativeLayout> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }
}