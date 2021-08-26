package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class SwipeConstraintLayout : ConstraintLayout {
    var delegate: SwipeViewDelegate? = null

    enum class Direction { Up, Down}

    private var lastY = 0f
    private var flagMoving = false
    private var dir = Direction.Down

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        post {
            reInit()
        }
    }

    @Suppress("RedundantOverride")
    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val maxY = height - context.resources.getDimensionPixelSize(R.dimen.dimen_16dp)

        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            // Calling performClick on every ACTION_DOWN so OnClickListener is triggered properly.
            performClick()
        }

        if (event != null && isEnabled) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    flagMoving = true
                    lastY = event.y

                    requestDisallowInterceptTouchEvent(true)

                }
                MotionEvent.ACTION_MOVE -> {
                    if (flagMoving) {
                        delegate?.onMove(y)
                        if (y >= 0) {
                            val diffY = (event.y - lastY) / 1.5f
                            var dy = translationY + diffY

                            when {
                                dy < 0 -> {
                                    dy = 0f
                                }
                                dy >= maxY -> {
                                    dy = maxY.toFloat()
                                }
                                else -> {
                                    dir = if (translationY < dy) Direction.Down else Direction.Up
                                }
                            }

                            translationY = dy
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {/*
                    if (dir == Direction.Down) {
                        animate().translationY(maxY.toFloat())
                    }
                    else {
                        animate().translationY(0f)
                    }*/
                    requestDisallowInterceptTouchEvent(false)
                    flagMoving = false
                }
            }

            return true
        }

        return super.onTouchEvent(event)
    }

    fun reInit() {
        val maxInitialHeight = context.resources.getDimensionPixelSize(R.dimen.dimen_300dp)
        val y = if (height > maxInitialHeight) (height - maxInitialHeight).toFloat()
        else 0f

        translationY = y
    }

}