package com.xiaomaoyuedan.live.ui.splash

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.xiaomaoyuedan.live.R
import com.xiaomaoyuedan.live.base.BaseActivity
import com.xiaomaoyuedan.live.bean.SplashBean
import com.xiaomaoyuedan.live.databinding.ActivitySplashBinding
import com.xiaomaoyuedan.live.ext.goActivity
import com.xiaomaoyuedan.live.ui.activity.MainActivity
import com.xiaomaoyuedan.live.viewModel.SplashViewModel
import com.xiaomaoyuedan.live.widget.CountDownView
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.parseState

class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>(),
    CountDownView.OnCountDownFinishListener {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var h5Url: String
    private var mStartActivity: Boolean = true

    override fun layoutId() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = OnViewClick()
        mDatabind.launcherCountdown.visibility = View.GONE
        mDatabind.launcherCountdown.setAddCountDownListener(this)
        mDatabind.launcherCountdown.setCountdownTime(3)
        viewModel.getSplashImage()
    }

    inner class OnViewClick {
        fun goMainActivity() {
            if (mStartActivity) {
                mStartActivity = false
                goActivity(this@SplashActivity,MainActivity::class.java)
                this@SplashActivity.finish()
            }
        }
    }

    override fun createObserver() {
        viewModel.splashImageResult.observe(this, { resultState ->
            parseState(resultState, {
                when (it.info.isNotEmpty()) {
                    true -> {
                        mDatabind.launcherCountdown.visibility = View.VISIBLE
                        val bean =
                            Gson().fromJson(Gson().toJson(it.info[0]), SplashBean::class.java)
                        h5Url = bean.url
                        Glide.with(this@SplashActivity).load(bean.image).into(mDatabind.ivSplash)
                        mDatabind.launcherCountdown.startCountDown()
                    }
                    false -> {
                        mDatabind.launcherCountdown.startCountDown()
                    }
                }
            }, {

            })
        })
    }

    /**
     * 倒计时结束
     */
    override fun countDownFinished() {
        OnViewClick().goMainActivity()
    }
}