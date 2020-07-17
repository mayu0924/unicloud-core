package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.iface.Helper

class XLinearLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>?> {
    private val mHelper: BaseHelper<XLinearLayout> =
        BaseHelper(context, this, attrs)

    override fun getHelper(): BaseHelper<*> {
        return mHelper
    }
}