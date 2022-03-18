package com.xiaomaoyuedan.live.net

import android.os.Build
import com.xiaomaoyuedan.live.bean.BaseResponseData
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 作者　:fzw
 * 时间　:
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        private const val isDeBug = true
        private const val SERVER_RELEASE = "https://api.hahamiao.com/api/"   // 正式环境
        private const val SOCKET_RELEASE = "http://learnsandroid.top:9092/" // socket 正式环境

        private const val SERVER_DEBUG = "https://apitest.hahamiao.com/api/" // 测试环境
        private const val SOCKET_DEBUG = "http://192.168.0.108:9092/"       // socket 测试环境
        var SOCKET_URL = if (isDeBug) SOCKET_DEBUG else SOCKET_RELEASE
        var SERVER_URL = if (isDeBug) SERVER_DEBUG else SERVER_RELEASE
        private const val ServerHost = "?service="
    }

    /****************************************项目接口****************************************/

    /*** 获取欢迎页图片 ***/
    @FormUrlEncoded
    @POST("?service=Home.GetStartPicture")
    suspend fun getWelcomeBg(@FieldMap map: HashMap<String, String>): ApiResponse<BaseResponseData>

}