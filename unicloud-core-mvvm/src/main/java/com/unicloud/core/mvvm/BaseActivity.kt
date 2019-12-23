package com.unicloud.core.mvvm

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.unicloud.core.mvvm.event.Message
import java.lang.reflect.ParameterizedType

/**
 *   @auther : Aleyn
 *   time   : 2019/11/01
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        createViewModel()
        startObserve()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
        initData()
    }

    abstract fun startObserve()
    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

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
            ToastUtils.showShort(it)
        })
        viewModel.defUI.msgEvent.observe(this, Observer {
            handleEvent(it)
        })
    }

    open fun handleEvent(msg: Message) {}

    /**
     * 打开等待框
     */
    open fun showLoading(){}

    /**
     * 关闭等待框
     */
    open fun dismissLoading() {}


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