package com.android.launcher4.util;

import android.view.MotionEvent;

public interface TouchController {
    boolean onTouchEvent(MotionEvent ev);
    boolean onInterceptTouchEvent(MotionEvent ev);
}
