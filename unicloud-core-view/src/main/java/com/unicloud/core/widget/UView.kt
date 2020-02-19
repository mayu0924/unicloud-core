package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper

class UView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), RHelper<UBaseHelper<*>?> {
    private val mHelper: UBaseHelper<View> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }
}