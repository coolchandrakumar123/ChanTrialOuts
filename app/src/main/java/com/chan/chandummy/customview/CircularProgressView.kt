package com.chan.chandummy.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.chan.chandummy.R
import com.chan.chandummy.util.getPixel

/**
 * Created by chandra-1765 on 09-Dec-2019.
 */
class CircularProgressView : View {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private val startAngle = 270f //12'o clock
    private var bgPaint: Paint = Paint()
    private var progressPaint: Paint = Paint()
    var percentage: Float = 65f
        set(value) {
            field = (value/100) * 360f
            invalidate()
        }

    var bgColor = Color.RED
    var progressColor = Color.BLUE
    var bgBarWidth = 10f.getPixel(context)
    var progressBarWidth = bgBarWidth + 4f.getPixel(context)
    var hideShadow = false

    var difference = progressBarWidth
    private fun init(attrs: AttributeSet? = null) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        attrs?.let {
            val attrDetails = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView)
            bgColor = attrDetails.getColor(R.styleable.CircularProgressView_bgColor, bgColor)
            progressColor = attrDetails.getColor(R.styleable.CircularProgressView_progressColor, progressColor)
            bgBarWidth = attrDetails.getDimension(R.styleable.CircularProgressView_bgBarWidth, bgBarWidth)
            progressBarWidth = attrDetails.getDimension(R.styleable.CircularProgressView_progressBarWidth, progressBarWidth)
            hideShadow = attrDetails.getBoolean(R.styleable.CircularProgressView_hideShadow, hideShadow)
            attrDetails.recycle()
        }
        difference = Math.max(progressBarWidth, bgBarWidth)
        bgPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = bgBarWidth
            strokeCap = Paint.Cap.ROUND
            color = bgColor
        }

        progressPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = progressBarWidth
            strokeCap = Paint.Cap.ROUND
            color = progressColor
            if(!hideShadow) {
                setShadowLayer(progressBarWidth, 0f, progressBarWidth/3, progressColor)
            }
        }
    }

    private var circleRect: RectF = RectF()
    override fun onDraw(canvas: Canvas) {
        circleRect.set(difference, difference, width.toFloat() - difference, height.toFloat() - difference)
        canvas.drawArc(circleRect, startAngle, 360f, false, bgPaint)
        canvas.drawArc(circleRect, startAngle, percentage, false, progressPaint)
    }

}