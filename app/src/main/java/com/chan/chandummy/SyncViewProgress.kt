package com.chan.chandummy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by chandra-1765 on 22-Mar-2019.
 */
class SyncViewProgress : View {
    private var strokePaint = Paint()
    private var progressPaint = Paint()
    var percentage = 30
        set(value) {
            field = value
            invalidate()
        }

    private fun init(context: Context) {
        this.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
        /*paint.apply {
            strokeWidth = 8f
            strokeCap = Paint.Cap.BUTT
            color = Color.GRAY
        }

        loadedPaint.apply {
            maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)
            alpha = 50
            strokeWidth = 8f
            strokeCap = Paint.Cap.BUTT
            color = Color.GREEN
        }*/

        strokePaint.apply {
            style = Paint.Style.STROKE
            color = Color.BLACK
            strokeWidth = 2.0f
        }
        progressPaint.apply {
            style = Paint.Style.FILL;
            color = Color.YELLOW;
        }


    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    public override fun onDraw(canvas: Canvas) {
//        canvas.drawRoundRect(20f, 10f, width.toFloat() - 20f, height.toFloat() - 20f, cornerRadius, cornerRadius, strokePaint);    // fill
//        canvas.drawRoundRect(50f, 50f, width.toFloat() - 50f, height.toFloat() - 50f, cornerRadius, cornerRadius, progressedPaint);  // stroke

        canvas.drawRoundRect(resources.getDimension(R.dimen.dimen_4), resources.getDimension(R.dimen.dimen_4), width - resources.getDimension(R.dimen.dimen_4), height - resources.getDimension(R.dimen.dimen_4), resources.getDimension(R.dimen.dimen_8), resources.getDimension(R.dimen.dimen_8), strokePaint)
        canvas.drawRoundRect(resources.getDimension(R.dimen.dimen_8), resources.getDimension(R.dimen.dimen_6), (width - resources.getDimension(R.dimen.dimen_8)) * (percentage / 100f), height - resources.getDimension(R.dimen.dimen_6), resources.getDimension(R.dimen.dimen_4), resources.getDimension(R.dimen.dimen_4), progressPaint)
    }

}