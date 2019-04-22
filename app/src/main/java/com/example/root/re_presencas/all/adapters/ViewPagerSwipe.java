package com.example.root.re_presencas.all.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerSwipe extends ViewPager {

    private boolean isEnabled;

    public ViewPagerSwipe(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.isEnabled = true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isEnabled) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isEnabled) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setSwippingEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
