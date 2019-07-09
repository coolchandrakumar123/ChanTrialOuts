package com.chan.chandummy.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.text.TextPaint
import android.util.AttributeSet


/**
 * Created by chandra-1765$ on 2019-06-16$.
 */

class CircleTextImageView : AppCompatImageView {
    var startColor = Color.WHITE
    var endColor = Color.LTGRAY

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        setWillNotDraw(false)
        if (attrs != null) {
            /*val attrDetails = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView)
            radius = attrDetails.getDimension(R.styleable.RoundedImageView_radius, 0f)
            attrDetails.recycle()*/
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        background = RoundBackgroundDrawable()
        super.setImageDrawable(drawable)
    }

    fun setText(value: String) {
        setImageDrawable(TextDrawable(value))
        invalidate()
    }

    inner class RoundBackgroundDrawable() : Drawable() {

        private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        private var path: Path = Path()

        init {
            paint.style = Paint.Style.FILL
            path.fillType = Path.FillType.EVEN_ODD
        }

        override fun onBoundsChange(bounds: Rect) {
            path.reset()
            val radius = (bounds.right - bounds.left).toFloat() / 2
            val x = bounds.left + radius
            val y = bounds.top + radius
            paint.shader = RadialGradient(x, y, radius, intArrayOf(startColor, endColor),
                    floatArrayOf(0.7f, 1.0f), Shader.TileMode.MIRROR)
            path.addCircle(x, y, radius, Path.Direction.CW)
        }

        override fun draw(canvas: Canvas) {
            canvas.drawPath(path, paint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(cf: ColorFilter?) {
            paint.colorFilter = cf
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }
    }

    inner class TextDrawable(val text: String) : Drawable() {
        private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private var textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

        init {
            paint.style = Paint.Style.FILL

            textPaint.textSize = 16f * resources.displayMetrics.scaledDensity
            textPaint.color = Color.BLACK
            textPaint.typeface = Typeface.DEFAULT_BOLD
        }

        override fun draw(canvas: Canvas) {

            val centerX = Math.round(bounds.width() * 0.5f).toFloat()
            val centerY = Math.round(bounds.height() * 0.5f).toFloat()

            val textWidth = textPaint.measureText(text) * 0.5f
            val textBaseLineHeight = textPaint.fontMetrics.ascent * -0.4f

            canvas.drawText(text, centerX - textWidth, centerY + textBaseLineHeight, textPaint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(cf: ColorFilter?) {
            paint.colorFilter = cf
        }

        override fun getOpacity(): Int {
            return PixelFormat.UNKNOWN
        }
    }
}
