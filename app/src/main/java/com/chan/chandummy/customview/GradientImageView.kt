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
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


/**
 * Created by chandra-1765$ on 2019-06-13$.
 */
class GradientImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private var mBorder: BorderDrawable? = null
    private fun init(context: Context) {
        setWillNotDraw(false)
        mBorder = BorderDrawable(Color.RED, paddingLeft, paddingLeft / 2)

        //setImageDrawable(gradientDrawable)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //gradientDrawable.setBounds()
        mBorder?.setBounds(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas) {
        //gradientDrawable?.draw(canvas)
        super.onDraw(canvas)
        mBorder?.draw(canvas)
    }

    /*private fun setDrawable() {
        drawable = object : Drawable() {
            fun draw(@NonNull canvas: Canvas) {

                val centerX = Math.round(canvas.getWidth() * 0.5f)
                val centerY = Math.round(canvas.getHeight() * 0.5f)

                *//*
        * To draw text
        * *//*
                if (text != null) {
                    val textWidth = textPaint.measureText(text) * 0.5f
                    val textBaseLineHeight = textPaint.getFontMetrics().ascent * -0.4f

                    *//*
            * Draw the background color before drawing initials text
            * *//*
                    if (shape == RECTANGLE) {
                        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
                    } else {
                        canvas.drawCircle(centerX,
                                centerY,
                                Math.max(canvas.getHeight() / 2, textWidth / 2),
                                paint)
                    }

                    *//*
            * Draw the text above the background color
            * *//*
                    canvas.drawText(text, centerX - textWidth, centerY + textBaseLineHeight, textPaint)
                }
            }

            override fun setAlpha(alpha: Int) {

            }

            override fun setColorFilter(colorFilter: ColorFilter?) {

            }

            override fun getOpacity(): Int {
                return PixelFormat.UNKNOWN
            }
        }
    }*/

    private fun getGlowDrawable(startColor: Int, endColor: Int): Drawable {
        val positiveShaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return RadialGradient(width.toFloat(), height.toFloat(),
                        width.toFloat(), intArrayOf(startColor, // please input your color from resource for color-4
                        endColor),
                        floatArrayOf(0.3f, 1.2f), Shader.TileMode.CLAMP)
            }
        }
        val positivePaintDrawable = PaintDrawable()
        positivePaintDrawable.shape = OvalShape()
        positivePaintDrawable.shaderFactory = positiveShaderFactory
        val positiveLayers = arrayOf<Drawable>(positivePaintDrawable)
        return LayerDrawable(positiveLayers)
    }

    inner class BorderDrawable(var mColor: Int, var mBorderWidth: Int, var mBorderRadius: Int) : Drawable() {

        private var mPaint: Paint

        private var mRect: RectF
        private var mPath: Path

        init {
            mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPaint.style = Paint.Style.FILL

            mPath = Path()
            mPath.setFillType(Path.FillType.EVEN_ODD)

            mRect = RectF()
        }

        override fun onBoundsChange(bounds: Rect) {
            mPath.reset()

            /*mPath.addRect(bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), Path.Direction.CW)
            mRect.set(bounds.left.toFloat() + mBorderWidth, bounds.top.toFloat() + mBorderWidth, bounds.right.toFloat() - mBorderWidth, bounds.bottom.toFloat() - mBorderWidth)
            mPath.addRoundRect(mRect, mBorderRadius.toFloat(), mBorderRadius.toFloat(), Path.Direction.CW)*/
            val radius = (bounds.right - bounds.left).toFloat() / 2
            val x = bounds.left + radius
            val y = bounds.top + radius
            mPath.addCircle(x, y, radius, Path.Direction.CW)
        }

        override fun draw(canvas: Canvas) {
            mPaint.color = mColor
            canvas.drawPath(mPath, mPaint)
        }

        override fun setAlpha(alpha: Int) {
            mPaint.alpha = alpha
        }

        override fun setColorFilter(cf: ColorFilter?) {
            mPaint.colorFilter = cf
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }
    }
}