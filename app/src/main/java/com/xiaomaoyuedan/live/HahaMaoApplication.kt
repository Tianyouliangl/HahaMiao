package com.xiaomaoyuedan.live

import androidx.multidex.MultiDex
import com.xiaomaoyuedan.live.liveData.AppViewModel
import com.tencent.mmkv.MMKV
import me.hgj.jetpackmvvm.base.BaseApp

//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val applicationViewModel: AppViewModel by lazy { HahaMaoApplication.appViewModelInstance }

class HahaMaoApplication : BaseApp(){

    companion object {
        lateinit var instance: HahaMaoApplication
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        instance = this
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        MultiDex.install(this)
    }
}