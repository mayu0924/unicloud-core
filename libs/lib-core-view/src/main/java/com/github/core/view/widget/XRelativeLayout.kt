package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.iface.Helper

class XRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>?> {
    private val mHelper: BaseHelper<XRelativeLayout> =
        BaseHelper(context, this, attrs)

    override fun getHelper(): BaseHelper<*> {
        return mHelper
    }
}