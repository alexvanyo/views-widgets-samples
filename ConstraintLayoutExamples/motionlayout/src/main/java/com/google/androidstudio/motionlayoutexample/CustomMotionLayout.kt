package com.google.androidstudio.motionlayoutexample

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.ViewCompat

class CustomMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private var undergoingMotion: Boolean = false

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        // super.onNestedScroll(...) {
        //     consumed[0] += dxUnconsumed;
        //     consumed[1] += dyUnconsumed;
        // }

        // Only add to consumed if we are still undergoing motion, or a child is consuming the scroll
        if (dxConsumed != 0 || dyConsumed != 0) {
            consumed[0] += dxUnconsumed
            consumed[1] += dyUnconsumed
        }
        // Reset undergoingMotion, as onNestedPreScroll will always set this back to true if we are still undergoing
        // motion
        undergoingMotion = false
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        // If consumed is non-zero, then this MotionLayout is changing its progress
        if (consumed[0] != 0 || consumed[1] != 0) {
            undergoingMotion = true
        }
    }
}
