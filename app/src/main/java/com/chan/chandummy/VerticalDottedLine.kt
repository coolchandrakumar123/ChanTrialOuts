package com.chan.chandummy

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.util.AttributeSet
import android.view.View

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

class VerticalDottedLine : View {

    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        val res = resources
        mPaint = Paint()
        mPaint?.let {
            it.color = Color.YELLOW
            val size = res.getDimensionPixelSize(R.dimen.line_width)
            val gap = res.getDimensionPixelSize(R.dimen.line_thickness)
            it.style = Paint.Style.FILL
            val path = Path()
            path.addCircle(0f, 0f, size.toFloat(), Path.Direction.CW)
            it.pathEffect = PathDashPathEffect(path, gap.toFloat(), 0f, PathDashPathEffect.Style.ROTATE)
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint?.let {
            canvas.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(), height.toFloat(), mPaint)
        }
    }
}