package com.chan.chandummy

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Created by chandra-1765 on 09/02/18.
 */
class DashedLineView : View {
    private lateinit var paint: Paint
    private var path: Path = Path()
    private var effects: PathEffect? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    /**
     * The intervals array controls the length of the dashes.
     * The paint's strokeWidth controls the thickness of the dashes.
     */
    private fun init(context: Context) {
        paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = resources.getDimensionPixelSize(R.dimen.line_thickness).toFloat()
        val density = resources.getDimensionPixelSize(R.dimen.line_width).toFloat()
        effects = DashPathEffect(floatArrayOf(density, density/2, density, density/2), 0f)

    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        paint.pathEffect = effects
        val measuredHeight = measuredHeight.toFloat()
        val measuredWidth = measuredWidth.toFloat()
        if (measuredHeight <= measuredWidth) {
            // horizontal
            path.moveTo(0f, 0f)
            path.lineTo(measuredWidth, 0f)
            canvas.drawPath(path, paint)
        } else {
            // vertical
            path.moveTo(0f, 0f)
            path.lineTo(0f, measuredHeight)
            canvas.drawPath(path, paint)
        }

    }
}