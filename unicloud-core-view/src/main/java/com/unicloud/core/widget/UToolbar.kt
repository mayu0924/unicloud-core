package com.unicloud.core.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.unicloud.core.widget.helper.UBaseHelper
import com.unicloud.core.widget.iface.RHelper
import java.lang.reflect.Method

class UToolbar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr), RHelper<UBaseHelper<*>?> {
    private val mHelper: UBaseHelper<View> =
        UBaseHelper(context, this, attrs)

    override fun getHelper(): UBaseHelper<*> {
        return mHelper
    }

    //R.mipmap.ic_toolbar_more_white
    fun init(activity: AppCompatActivity, overflowIcon: Int?): UToolbar {
        activity.setSupportActionBar(this)
        activity.supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        overflowIcon?.let { this.overflowIcon = resources.getDrawable(overflowIcon) }
        return this
    }

    fun createOptionMenu(activity: AppCompatActivity, menuRes: Int?, menu: Menu?) {
        menuRes?.let {
            activity.menuInflater.inflate(it, menu)
        }
    }

    fun prepareOptionsMenu(menu: Menu?) {
        //使菜单上图标可见
        if (menu != null && menu is MenuBuilder) { //编sdk版本24的情况 可以直接使用 setOptionalIconsVisible
            if (Build.VERSION.SDK_INT > 23) {
                val builder: MenuBuilder = menu as MenuBuilder
                builder.setOptionalIconsVisible(true)
            } else { //sdk版本24的以下，需要通过反射去执行该方法
                try {
                    val m: Method = menu.javaClass
                        .getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    m.isAccessible = true
                    m.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun navigationClickListener(listener: (v: View) -> Unit): UToolbar {
        setNavigationOnClickListener {
            listener.invoke(it)
        }
        return this
    }

    fun menuItemClickListener(listener: (menuItem: MenuItem) -> Unit): UToolbar {
        setOnMenuItemClickListener {
            listener.invoke(it)
            true
        }
        return this
    }
}