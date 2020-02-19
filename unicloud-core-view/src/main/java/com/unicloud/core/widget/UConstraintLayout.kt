package com.unicloud.core.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper

class UConstraintLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    RHelper<UBaseHelper<*>> {
    private val mHelper: UBaseHelper<UConstraintLayout> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }
}