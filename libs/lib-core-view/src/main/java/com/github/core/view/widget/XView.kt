package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.iface.Helper

class XView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>?> {
    private val mHelper: BaseHelper<View> =
        BaseHelper(context, this, attrs)

    override fun getHelper(): BaseHelper<*> {
        return mHelper
    }
}