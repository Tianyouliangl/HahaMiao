package com.xiaomaoyuedan.live.viewModel

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.xiaomaoyuedan.live.bean.BaseResponseData
import com.xiaomaoyuedan.live.net.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class SplashViewModel : BaseViewModel() {

    var splashImageResult = MutableLiveData<ResultState<BaseResponseData>>()

    fun getSplashImage() {
        var map = HashMap<String, String>()
        map["version"] = "2.0.0"
        map["model"] = Build.MODEL
        map["system"] = Build.VERSION.RELEASE
        request({ apiService.getWelcomeBg(map) }, splashImageResult, false, "")
    }

}