package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MySlidingUpPanelLayout: SlidingUpPanelLayout {
    var isChildScrollable = false

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (! isChildScrollable) {
            super.onInterceptTouchEvent(ev)
        } else {
            super.onInterceptTouchEvent(ev)
            false
        }
    }
}