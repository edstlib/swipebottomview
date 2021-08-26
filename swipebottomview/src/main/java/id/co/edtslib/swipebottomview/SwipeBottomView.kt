package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.widget.TextViewCompat

class SwipeBottomView: FrameLayout {
    var delegate: SwipeViewDelegate? = null
    private var swipeView: SwipeView? = null

    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        visibility = INVISIBLE

        inflate(context, R.layout.view_bottom_swipe, this)

        swipeView = findViewById(R.id.swipeView)

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SwipeBottomView,
                0, 0
            )

            val title = a.getString(R.styleable.SwipeBottomView_title)
            swipeView?.setTitle(title)

            val content = a.getResourceId(R.styleable.SwipeBottomView_content, 0)
            if (content != 0) {
                swipeView?.setContentView(content)
            }

            val bottom = a.getResourceId(R.styleable.SwipeBottomView_bottom, 0)
            if (content != 0) {
                swipeView?.setBottomView(bottom)
            }

            val textStyle = a.getResourceId(R.styleable.SwipeBottomView_titleStyle, 0)
            if (textStyle > 0) {
                swipeView?.setTitleStyle(textStyle)
            }

            a.recycle()
        }

        swipeView?.delegate = object : SwipeViewDelegate {
            override fun onMove(y: Float) {
                delegate?.onMove(y)
            }
        }
    }

    fun getContentView() = swipeView?.contentView
    fun getBottomView() = swipeView?.bottomView
    fun relayout() = swipeView?.relayout()

    override fun setVisibility(visibility: Int) {
        super.setVisibility(VISIBLE)
        swipeView?.visibility = visibility
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        swipeView?.isEnabled = enabled
    }
}