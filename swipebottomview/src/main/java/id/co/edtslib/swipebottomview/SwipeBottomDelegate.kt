package id.co.edtslib.swipebottomview

interface SwipeBottomDelegate {
    fun onSwiping(offset: Float)
    fun onStartSwiping()
    fun onExpanded()
    fun onCollapsed()
}