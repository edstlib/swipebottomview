package id.co.edtslib.swipebottomview

import android.view.View

interface SwipeBottomDelegate {
    fun onSwiping(panel: View, offset: Float)
    fun onStartSwiping()
    fun onExpanded()
    fun onCollapsed()
}