package com.github.core.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.github.core.view.widget.helper.BaseHelper
import com.github.core.view.widget.helper.ToolbarHelper
import com.github.core.view.widget.iface.Helper
import java.lang.reflect.Field
import java.lang.reflect.Method


class XToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr),
    Helper<BaseHelper<*>?> {

    private var mHelper: ToolbarHelper? = null

    private val mTitleTextView: AppCompatTextView by lazy {
        getTitleTextView()!!
    }

    private var mTitleTextViewWidth: Int = 0

    init {
        mHelper = ToolbarHelper(
            context,
            this,
            attrs
        )
        if (mHelper?.mIsCenterTitle == true)
            updateTitleCenter()
    }

    override fun getHelper(): BaseHelper<*>? {
        return mHelper
    }

    //R.mipmap.ic_toolbar_more_white
    @SuppressLint("RestrictedApi")
    fun init(activity: AppCompatActivity, overflowIcon: Int?): XToolbar {
        activity.setSupportActionBar(this)
        activity.supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        overflowIcon?.let { this.overflowIcon = resources.getDrawable(overflowIcon) }
        return this
    }

    var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    fun updateTitleCenter() {
        if (globalLayoutListener == null) {
            globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                if (mHelper?.mIsCenterTitle == true) {
                    update()
                }
            }
        } else {
            viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun update() {
        mTitleTextView.apply {
            gravity = Gravity.CENTER
            val lp = layoutParams
            lp.width = LayoutParams.MATCH_PARENT
            layoutParams = lp

            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels

            val location = intArrayOf(0, 0)
            getLocationOnScreen(location)
            val tLeft = location[0]

            val tRight = screenWidth - tLeft - this.width

            if (tLeft < tRight) {
                setPadding(tRight - tLeft, 0, 0, 0)
            } else {
                setPadding(0, 0, tLeft - tRight, 0)
            }
            if (this.width == mTitleTextViewWidth && globalLayoutListener != null) {
                viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
                globalLayoutListener = null
            }
            mTitleTextViewWidth = this.width
        }
    }

    fun navigationClickListener(listener: (v: View) -> Unit): XToolbar {
        setNavigationOnClickListener {
            listener.invoke(it)
        }
        return this
    }

    fun menuItemClickListener(listener: (menuItem: MenuItem) -> Unit): XToolbar {
        setOnMenuItemClickListener {
            listener.invoke(it)
            true
        }
        return this
    }


    fun <T> getTView(mFiled: String): T? {
        try {
            val field: Field =
                Toolbar::class.java.getDeclaredField(mFiled)
            field.isAccessible = true
            return field.get(this) as T
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: TypeCastException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取TitleTextView
     * @return
     */
    fun getTitleTextView(): AppCompatTextView? {
        return getTView<AppCompatTextView>("mTitleTextView")
    }

    private fun getSubTitleTextView(): AppCompatTextView? {
        return getTView<AppCompatTextView>("mSubtitleTextView")
    }

    private fun getActionMenuView(): ActionMenuView? {
        return getTView<ActionMenuView>("mMenuView")
    }

    private fun NavButtonView(): ImageButton? {
        return getTView<ImageButton>("mNavButtonView")
    }

    private fun getLogoView(): ImageView? {
        return getTView<ImageView>("mLogoView")
    }

    fun getExpandedActionView(): View? {
        return getTView<View>("mExpandedActionView")
    }

    companion object {
        @SuppressLint("RestrictedApi")
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
    }
}