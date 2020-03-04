package com.unicloud.core.mvvm

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.mvvm.dialog.BaseDialog
import com.unicloud.core.mvvm.dialog.LoadingDialog
import com.unicloud.core.mvvm.event.Message
import com.unicloud.core.mvvm.utils.StatusBarUtil
import me.jessyan.autosize.internal.CustomAdapt
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), CustomAdapt {

    override fun isBaseOnWidth(): Boolean = true

    override fun getSizeInDp(): Float = 360f

    protected open fun setToolbar(): Toolbar? = null

    private var mToolbar: Toolbar? = null

    lateinit var viewModel: VM

    //是否第一次加载
    private var isFirst: Boolean = true

    private var mLoadingDialog: BaseDialog? = null

    private fun initLoadingDialog() {
        if (mLoadingDialog == null)
            mLoadingDialog = LoadingDialog(activity)
    }

    open fun initToolbar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(activity!!, Color.parseColor("#000000"))
        } else if (mToolbar != null) {
            StatusBarUtil.setGradientColor(activity!!, mToolbar)
            StatusBarUtil.setDarkMode(activity!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mToolbar = setToolbar()
        initToolbar()

        onVisible()
        createViewModel()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
    }

    override fun onPause() {
        if (KeyboardUtils.isSoftInputVisible(activity!!)) KeyboardUtils.hideSoftInput(activity!!)
        super.onPause()
    }

    override fun onDetach() {
        dismissLoading()
        if (KeyboardUtils.isSoftInputVisible(activity!!)) KeyboardUtils.hideSoftInput(activity!!)
        super.onDetach()

    }

    open fun initView(savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    abstract fun layoutId(): Int

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel.defUI.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(viewLifecycleOwner, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.defUI.msgEvent.observe(viewLifecycleOwner, Observer {
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