package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat

class SwipeView: FrameLayout {
    var delegate: SwipeViewDelegate? = null
    var contentView: View? = null
    var bottomView: View? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        visibility = INVISIBLE

        inflate(context, R.layout.view_swipe, this)

        val clContent = findViewById<SwipeConstraintLayout>(R.id.clContent)
        clContent.delegate = object : SwipeViewDelegate {
            override fun onMove(y: Float) {
                if (contentView is SwipeRecyclerView) {
                    (contentView as SwipeRecyclerView).canScroll = y <= 0
                }
                delegate?.onMove(y)
            }
        }
    }

    fun setTitle(title: String?) {
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = title

    }

    fun setTitleStyle(style: Int) {
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        TextViewCompat.setTextAppearance(tvTitle, style)

    }

    fun setContentView(content: Int) {
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutContent)
        contentView = inflate(context, content, null)
        frameLayout.addView(contentView)

        if (contentView is SwipeRecyclerView) {
            (contentView as SwipeRecyclerView).canScroll = false
        }
    }

    fun setBottomView(bottom: Int) {
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutBottom)
        bottomView = inflate(context, bottom, frameLayout)
    }

    fun relayout() {
        post {
            visibility = VISIBLE

            val clContent = findViewById<SwipeConstraintLayout>(R.id.clContent)
            clContent.reInit()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled) {
            val clContent = findViewById<SwipeConstraintLayout>(R.id.clContent)
            clContent.y = 1f

            if (contentView is SwipeRecyclerView) {
                val recyclerView = contentView as SwipeRecyclerView
                recyclerView.canScroll = false
                recyclerView.layoutManager?.scrollToPosition(0)
            }
        }

        super.setEnabled(enabled)
    }

    /*
    override fun setVisibility(visibility: Int) {
        if (visibility == VISIBLE) {
            show()
        }
        else
            if (visibility == GONE) {
                hide()
            }
    }

    private fun show() {
        post {
            translationY = height.toFloat()

            animate().translationY(0f)
            super.setVisibility(VISIBLE)
        }
    }

    private fun hide() {
        post {
            translationY = height.toFloat()

            animate().translationY(height.toFloat())
            super.setVisibility(View.INVISIBLE)
        }
    }*/
}