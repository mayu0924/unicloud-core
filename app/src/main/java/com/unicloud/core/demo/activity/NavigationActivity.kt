package com.unicloud.core.demo.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_navigation.*

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/5/9 10:23
 *     desc   : Navigation-Fragment
 *     version: 1.0
 * </pre>
 */
class NavigationActivity : BaseActivity<NoViewModel>() {
    override fun layoutId() = R.layout.activity_navigation

    override fun setToolbar(): Toolbar = toolBar

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setLightMode(this)
        toolBar.init(this, R.mipmap.ic_toolbar_more_white)
            .setNavigationOnClickListener { onBackPressed() }
    }

    override fun initData() {

    }

    override fun startObserve() {

    }
}