package com.unicloud.core.demo

import androidx.lifecycle.MutableLiveData
import com.unicloud.core.demo.model.bean.ArticleListBean
import com.unicloud.core.demo.model.repository.MainRepository
import com.unicloud.core.mvvm.BaseViewModel
import com.unicloud.core.mvvm.event.Message

class MainViewModel : BaseViewModel() {
    private val mainRespository by lazy { MainRepository() }
    val mArticleListBean: MutableLiveData<ArticleListBean> = MutableLiveData()

    fun getHomeArticles() {
        launchOnlyresult({
            mainRespository.getHomeArticles(0)
        }, {
            mArticleListBean.value = it!!
            defUI.toastEvent.postValue(it.datas[0].title)
            defUI.msgEvent.postValue(Message(0, "请求成功", it.datas[0].title))
        })
    }
}