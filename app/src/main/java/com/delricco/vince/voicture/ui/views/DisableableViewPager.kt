package com.delricco.vince.voicture.ui.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class DisableableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var pagingEnabled = true

    fun setPagingEnabled(enabled: Boolean) {
        pagingEnabled = enabled
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return pagingEnabled && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return pagingEnabled && super.onInterceptTouchEvent(ev)
    }
}