package com.xiaomaoyuedan.live.widget

import android.animation.Animator
import kotlin.jvm.JvmOverloads
import android.graphics.RectF
import android.animation.ValueAnimator
import android.util.TypedValue
import android.view.animation.LinearInterpolator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.xiaomaoyuedan.live.R

class CountDownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //圆轮颜色
    private val mRingColor: Int

    //圆轮宽度
    private val mRingWidth: Float

    //圆轮进度值文本大小
    private val mRingProgessTextSize: Int

    //宽度
    private var mWidth = 0

    //高度
    private var mHeight = 0
    private val mPaint: Paint

    //圆环的矩形区域
    private var mRectF: RectF? = null

    //
    private val mProgessTextColor: Int
    private var mCountdownTime: Int
    private var mCurrentProgress = 0f
    private var mListener: OnCountDownFinishListener? = null
    var valueAnimator: ValueAnimator? = null
    private fun sp2px(context: Context, spValue: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spValue.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    fun setCountdownTime(mCountdownTime: Int) {
        this.mCountdownTime = mCountdownTime
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRectF = RectF(
            0 + mRingWidth / 2, 0 + mRingWidth / 2,
            mWidth - mRingWidth / 2, mHeight - mRingWidth / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 圆环
         */
        //颜色
        mPaint.color = mRingColor
        //空心
        mPaint.style = Paint.Style.STROKE
        //宽度
        mPaint.strokeWidth = mRingWidth
        canvas.drawArc(mRectF!!, -90f, mCurrentProgress - 360, false, mPaint)
        //绘制文本
        val textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        //        String text = mCountdownTime - (int) (mCurrentProgress / 360f * mCountdownTime) + "";
        val text = "跳过"
        textPaint.textSize = mRingProgessTextSize.toFloat()
        textPaint.color = mProgessTextColor

        //文字居中显示
        val fontMetrics = textPaint.fontMetricsInt
        val baseline =
            ((mRectF!!.bottom + mRectF!!.top - fontMetrics.bottom - fontMetrics.top) / 2).toInt()
        canvas.drawText(text, mRectF!!.centerX(), baseline.toFloat(), textPaint)
    }

    private fun getValA(countdownTime: Long): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 100f)
        valueAnimator.duration = countdownTime
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatCount = 0
        return valueAnimator
    }

    /**
     * 开始倒计时
     */
    fun startCountDown() {
//        setClickable(false);
        valueAnimator = getValA((mCountdownTime * 1000).toLong())
        valueAnimator!!.addUpdateListener { animation ->
            val i = java.lang.Float.valueOf(animation.animatedValue.toString())
            mCurrentProgress = (360 * (i / 100f))
            invalidate()
        }
        valueAnimator!!.start()
        valueAnimator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //倒计时结束回调
                if (mListener != null) {
                    mListener!!.countDownFinished()
                }
            }
        })
    }

    fun setAddCountDownListener(mListener: OnCountDownFinishListener?) {
        this.mListener = mListener
    }

    interface OnCountDownFinishListener {
        fun countDownFinished()
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CountDownView)
        mRingColor = a.getColor(
            R.styleable.CountDownView_ringColor,
            context.resources.getColor(R.color.colorAccent)
        )
        mRingWidth = a.getFloat(R.styleable.CountDownView_ringWidth, 40f)
        mRingProgessTextSize =
            a.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, sp2px(context, 20))
        mProgessTextColor = a.getColor(
            R.styleable.CountDownView_progressTextColor,
            context.resources.getColor(R.color.colorAccent)
        )
        mCountdownTime = a.getInteger(R.styleable.CountDownView_countdownTime, 60)
        a.recycle()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.isAntiAlias = true
        setWillNotDraw(false)
    }
}