package com.github.core.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.iface.Helper

class XConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>> {
    private val mHelper: BaseHelper<XConstraintLayout> =
        BaseHelper(context, this, attrs)

    override fun getHelper(): BaseHelper<*> {
        return mHelper
    }
}