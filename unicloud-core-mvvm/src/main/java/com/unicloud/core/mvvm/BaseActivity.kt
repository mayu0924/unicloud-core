package com.unicloud.core.mvvm

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.hchstudio.kpermissions.KPermission
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.mvvm.dialog.BaseDialog
import com.unicloud.core.mvvm.dialog.LoadingDialog
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.utils.NetworkManager
import com.unicloud.core.mvvm.utils.SoftKeyBoardListener
import com.unicloud.core.mvvm.utils.StatusBarUtil
import com.unicloud.core.utils.filter.InputEmojiFilter
import me.jessyan.autosize.internal.CustomAdapt
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), CustomAdapt,
    NetworkManager.OnNetworkChangedListener {
    var isNetAvailable = true

    private var mLoadingDialog: BaseDialog? = null

    private var mNetworkManager: NetworkManager? = null

    lateinit var mKPermission: KPermission

    lateinit var viewModel: VM

    override fun isBaseOnWidth(): Boolean = true

    override fun getSizeInDp(): Float = 360f

    protected open fun setToolbar(): Toolbar? = null

    protected open fun toolbarMenuRes(): Int? = null

    private fun initLoadingDialog() {
        if (mLoadingDialog == null)
            mLoadingDialog = LoadingDialog(this)
    }

    // =====================================================
    abstract fun startObserve()

    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    // =====================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        val toolbar: Toolbar? = setToolbar()
        mKPermission = KPermission(this)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(this, Color.parseColor("#000000"))
        } else if (toolbar != null) {
            StatusBarUtil.setGradientColor(this, toolbar)
            StatusBarUtil.setDarkMode(this)
        }

        SoftKeyBoardListener.setListener(this, object :
            SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                onSoftInputHeightChanged(height, true)
            }

            override fun keyBoardHide(height: Int) {
                onSoftInputHeightChanged(height, false)
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkManager = NetworkManager(this)
            mNetworkManager?.register(this)
        }

        createViewModel()
        startObserve()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
        initData()
    }

    open fun onSoftInputHeightChanged(height: Int, isShow: Boolean) {
        LogUtils.d("BaseActivity", "onSoftInputChanged: $height ,isShow: $isShow")
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isNetAvailable) {
                mNetworkManager?.dismissDialog()
            } else {
                mNetworkManager?.showDialog()
            }
        }
    }

    override fun onPause() {
        if (KeyboardUtils.isSoftInputVisible(this)) KeyboardUtils.hideSoftInput(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkManager?.dismissDialog()
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkManager?.unRegister()
        }
    }

    override fun finish() {
        dismissLoading()
        if (KeyboardUtils.isSoftInputVisible(this)) KeyboardUtils.hideSoftInput(this)
        super.finish()
    }

    override fun onAvailable() {
        isNetAvailable = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkManager?.dismissDialog()
        }
    }

    override fun unAvailable() {
        isNetAvailable = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkManager?.showDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mKPermission.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        toolbarMenuRes()?.let {
            menuInflater.inflate(it, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
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
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showLong(it)
        })
        viewModel.defUI.msgEvent.observe(this, Observer {
            handleEvent(it)
        })
    }

    open fun handleEvent(msg: Message) {}

    /**
     * 打开等待框
     */
    private fun showLoading() {
        initLoadingDialog()
        mLoadingDialog?.show()
    }

    /**
     * 关闭等待框
     */
    private fun dismissLoading() {
        mLoadingDialog?.dismiss()
    }


    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this, ViewModelFactory()).get(tClass) as VM
        }
    }

}