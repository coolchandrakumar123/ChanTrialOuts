package com.chan.chandummy.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
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
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.chan.chandummy.R


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

    private var mBorder: Drawable? = null
    private fun init(context: Context) {
        setWillNotDraw(false)
        //mBorder = BorderDrawable(Color.RED, paddingLeft, paddingLeft / 2)
        mBorder = getGlowFromDrawable(Color.RED, Color.WHITE)

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

    private fun getGlowFromDrawable(startColor: Int, endColor: Int): Drawable? {
        val circleDrawable = context.getDrawable(R.drawable.circle_bg)?:return null
        val positiveShaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(0f,0f, 0f, height.toFloat(), intArrayOf(startColor, startColor, endColor, endColor),
                        floatArrayOf(0.0f, 0.5f, 0.5f, 0.9f), Shader.TileMode.CLAMP)
                return RadialGradient(width.toFloat()/2, height.toFloat()/2,
                        width.toFloat()/2, intArrayOf(startColor, endColor),
                        floatArrayOf(0.2f, 0.9f), Shader.TileMode.CLAMP)
            }
        }
        val positivePaintDrawable = PaintDrawable()
        //positivePaintDrawable.shape = OvalShape()
        positivePaintDrawable.shaderFactory = positiveShaderFactory
        val positiveLayers = arrayOf<Drawable>(circleDrawable, positivePaintDrawable)
        return LayerDrawable(positiveLayers)
        //val sds = ShapeDrawable(circleDrawable)

        /*val rss = RoundRectShape(floatArrayOf(12f, 12f, 12f,
                12f, 12f, 12f, 12f, 12f), null, null)
        val sds = ShapeDrawable(rss)
        sds.shaderFactory = object : ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(0, 0, 0, height, intArrayOf(Color.parseColor("#e5e5e5"),
                        Color.parseColor("#e5e5e5"),
                        Color.parseColor("#e5e5e5"),
                        Color.parseColor("#e5e5e5")), floatArrayOf(0f,
                        0.50f, 0.50f, 1f), Shader.TileMode.REPEAT)
            }
        }

        val ld = LayerDrawable(arrayOf<Drawable>(sds, sd))
        ld.setLayerInset(0, 5, 5, 0, 0) // inset the shadow so it doesn't start right at the left/top

        ld.setLayerInset(1, 0, 0, 5, 5) // inset the top drawable so we can leave a bit of space for the shadow to use


        b.setBackgroundDrawable(ld)*/
    }

    private fun getGlowDrawable(startColor: Int, endColor: Int): Drawable {
        val positiveShaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return RadialGradient(width.toFloat()/2, height.toFloat()/2,
                        width.toFloat()/2, intArrayOf(startColor, endColor),
                        floatArrayOf(0.2f, 0.9f), Shader.TileMode.CLAMP)
            }
        }
        val positivePaintDrawable = PaintDrawable()
        positivePaintDrawable.shape = OvalShape()
        positivePaintDrawable.shaderFactory = positiveShaderFactory
        val positiveLayers = arrayOf<Drawable>(positivePaintDrawable)
        return LayerDrawable(positiveLayers)
    }

    inner class BorderDrawable(var mColor: Int, var mBorderWidth: Int, var mBorderRadius: Int) : Drawable() {

        private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        private var mRect: RectF
        private var mPath: Path

        init {
            mPaint.style = Paint.Style.FILL

            mPath = Path()
            mPath.fillType = Path.FillType.EVEN_ODD

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