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
    var mainView: View? = null
    var slideView: View? = null
    var bottomView: View? = null
    var slidingUpPanel: MySlidingUpPanelLayout? = null
    var initialHeightPct = 0.3f

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
                if (panel != null) {
                    delegate?.onSwiping(panel, slideOffset)
                }
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

            val content = a.getResourceId(R.styleable.SwipeBottomView_main, 0)
            if (content != 0) {
                val frameLayout = findViewById<FrameLayout>(R.id.flMain)
                mainView = inflate(context, content, frameLayout)
            }

            val slide = a.getResourceId(R.styleable.SwipeBottomView_slide, 0)
            if (slide != 0) {
                val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutContent)
                slideView = inflate(context, slide, frameLayout)
            }

            val bottom = a.getResourceId(R.styleable.SwipeBottomView_bottom, 0)
            if (bottom != 0) {
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
            relayout()
        }
    }


    fun expand() {
        slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }

    fun collapse() {
        slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    fun relayout() {
        val clContent = findViewById<View>(R.id.clContent)

        val h = clContent.height
        val max = height*initialHeightPct

        slidingUpPanel?.anchorPoint = if (h > max && h > 0) max/h else 1f
        slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
    }

    fun setIsChildScrollable(scrollable: Boolean) {
        slidingUpPanel?.isChildScrollable = scrollable
    }

    fun setTittle(resId: Int) {
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.setText(resId)

    }

    fun setSlideView(layoutId: Int): View? {
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutContent)
        frameLayout.removeAllViews()
        slideView = inflate(context, layoutId, frameLayout)

        return slideView
    }

    fun setBottomView(layoutId: Int): View? {
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayoutBottom)
        frameLayout.removeAllViews()
        bottomView = inflate(context, layoutId, frameLayout)

        return bottomView
    }

}