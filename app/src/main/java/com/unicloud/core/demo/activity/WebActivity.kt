package com.unicloud.core.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.*
import android.webkit.WebSettings.LayoutAlgorithm
import com.blankj.utilcode.util.LogUtils
import com.unicloud.core.demo.R
import com.unicloud.core.mvvm.BaseActivity
import com.unicloud.core.mvvm.NoViewModel
import com.unicloud.core.mvvm.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : BaseActivity<NoViewModel>() {

    private val TAG = this::class.java.simpleName

    override fun layoutId(): Int = R.layout.activity_web

    override fun startObserve() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setDarkMode(this)
        toolBar.init(this, null).navigationClickListener { onBackPressed() }
        toolBar.navigationIcon = null
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                LogUtils.d(TAG, "url:$url")
                return false
            }
        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                LogUtils.d(TAG, "title:$title")
                if (title == null) {
                    toolBar.title = ""
                } else {
                    toolBar.title = if (title.contains(".", true) || title.contains("/", true)) "" else title
                }
                if (webview.canGoBack()) {
                    toolBar.navigationIcon = resources.getDrawable(R.mipmap.common_backbtn_normal)
                } else {
                    toolBar.navigationIcon = null
                }
                toolBar.updateTitleCenter()
            }
        }
        val webSetting: WebSettings = webview.getSettings()
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(false)
        // webSetting.setLoadWithOverviewMode(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.javaScriptEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting.setAppCachePath(getDir("appcache", 0).path)
        webSetting.databasePath = getDir("databases", 0).path
        webSetting.setGeolocationDatabasePath(
            getDir("geolocation", 0)
                .path
        )
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
    }

    override fun initData() {
        val url = intent.getStringExtra("url")?:"http://www.baidu.com"
        webview.loadUrl(url)
        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().sync()
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            finish()
        }
    }
}