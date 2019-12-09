package com.chan.chandummy.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View


/**
 * Created by chandra-1765$ on 2019-06-16$.
 */

class CircleView : View {

    private var circleColor = Color.RED
        set(circleColor) {
            field = circleColor
            invalidate()
        }

    private var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width
        val h = height

        val pl = paddingLeft
        val pr = paddingRight
        val pt = paddingTop
        val pb = paddingBottom

        val usableWidth = w - (pl + pr)
        val usableHeight = h - (pt + pb)

        val radius = Math.min(usableWidth, usableHeight) / 2
        val cx = pl + usableWidth / 2
        val cy = pt + usableHeight / 2

        paint.color = this.circleColor
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint)
    }
}
