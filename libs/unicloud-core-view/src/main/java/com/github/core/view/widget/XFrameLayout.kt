package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.iface.Helper

class XFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>?> {
    private val mHelper: BaseHelper<XFrameLayout> =
        BaseHelper(context, this, attrs)

    override fun getHelper(): BaseHelper<*> {
        return mHelper
    }
}