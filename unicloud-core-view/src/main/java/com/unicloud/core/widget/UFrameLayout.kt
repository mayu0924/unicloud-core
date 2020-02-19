package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper

class UFrameLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context!!, attrs, defStyleAttr), RHelper<UBaseHelper<*>?> {
    private val mHelper: UBaseHelper<UFrameLayout> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }
}