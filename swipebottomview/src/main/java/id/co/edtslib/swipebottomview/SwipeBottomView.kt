package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SwipeBottomView: FrameLayout {
    var delegate: SwipeBottomDelegate? = null
    var contentView: View? = null
    var bottomView: View? = null
    private var slidingUpPanel: MySlidingUpPanelLayout? = null
    private var initialHeightPct = 0.4f

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

        slidingUpPanel = findViewById(R.id.slidingUpPanel)
        slidingUpPanel?.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                delegate?.onSwiping(slideOffset)
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        delegate?.onCollapsed()
                    }
                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        delegate?.onExpanded()
                    }
                    SlidingUpPanelLayout.PanelState.DRAGGING -> {
                        delegate?.onStartSwiping()
                    }
                    else -> {
                        // ignore
                    }
                }
            }

        })

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SwipeBottomView,
                0, 0
            )

            val title = a.getString(R.styleable.SwipeBottomView_title)

            val tvTitle = findViewById<TextView>(R.id.tvTitle)
            tvTitle.text = title

            val content = a.getResourceId(R.styleable.SwipeBottomView_content, 0)
            if (content != 0) {
                val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutContent)
                contentView = inflate(context, content, null)
                frameLayout.addView(contentView)
            }

            val bottom = a.getResourceId(R.styleable.SwipeBottomView_bottom, 0)
            if (content != 0) {
                val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutBottom)
                bottomView = inflate(context, bottom, frameLayout)
            }

            val textStyle = a.getResourceId(R.styleable.SwipeBottomView_titleStyle, 0)
            if (textStyle > 0) {
                TextViewCompat.setTextAppearance(tvTitle, textStyle)
            }

            initialHeightPct = a.getFloat(R.styleable.SwipeBottomView_initialHeightPct, 0.3f)

            a.recycle()
        }

        post {
            var h = 0
            if (contentView != null) {
                h += contentView!!.height
            }
            if (bottomView != null) {
                h += bottomView!!.height
            }

            if (h < height) {
                val llSlide = findViewById<View>(R.id.llSlide)
                llSlide.layoutParams.height = h
            }

            val max = height*initialHeightPct

            slidingUpPanel?.anchorPoint = if (h > max) initialHeightPct else 1f
            slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
    }
}