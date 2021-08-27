package id.co.edtslib.swipebottomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class SwipeBottomView: FrameLayout {
    var delegate: SlidingUpPanelLayout.PanelSlideListener? = null
    var contentView: View? = null
    var bottomView: View? = null
    private var slidingUpPanel: MySlidingUpPanelLayout? = null
    private var initialHeightPct = 0.35f

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
        slidingUpPanel?.addPanelSlideListener(delegate)

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

            post {
                val max = height*initialHeightPct
                if (h > max) {
                    slidingUpPanel?.anchorPoint = initialHeightPct
                    slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                }
                else {
                    slidingUpPanel?.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                }


                visibility = VISIBLE
            }
        }
    }
}