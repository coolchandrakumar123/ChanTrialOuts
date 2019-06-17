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
import android.graphics.RadialGradient
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


/**
 * Created by chandra-1765$ on 2019-06-16$.
 */

class CircleBgImageView : AppCompatImageView {
    var radius: Float = 20.toFloat()

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

    override fun setImageBitmap(image: Bitmap) {
        background = RoundBackgroundDrawable(Color.RED)
        setImageDrawable(RoundImageDrawable(image, radius))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        background = RoundBackgroundDrawable(Color.RED)
        super.setImageDrawable(RoundImageDrawable.fromDrawable(drawable, radius))
    }

    inner class RoundBackgroundDrawable(var mColor: Int) : Drawable() {

        private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        private var path: Path

        init {
            paint.style = Paint.Style.FILL

            path = Path()
            path.fillType = Path.FillType.EVEN_ODD

            paint.color = mColor
        }

        override fun onBoundsChange(bounds: Rect) {
            path.reset()
            val radius = (bounds.right - bounds.left).toFloat() / 2
            val x = bounds.left + radius
            val y = bounds.top + radius
            paint.shader = RadialGradient(x, y, radius, intArrayOf(Color.WHITE, Color.LTGRAY),
                    floatArrayOf(0.7f, 1.0f), Shader.TileMode.CLAMP)
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

    class RoundImageDrawable(val bitmap: Bitmap, private val cornerRadius: Float) : Drawable() {
        private val paint: Paint = Paint()
        private val rectF: RectF = RectF()

        companion object {
            fun fromBitmap(bitmap: Bitmap?): RoundImageDrawable? {
                return if (bitmap != null) {
                    RoundImageDrawable(bitmap, 10.0f)
                } else {
                    null
                }
            }

            fun fromDrawable(drawable: Drawable?, radius: Float): Drawable? {
                if (drawable != null) {
                    if (drawable is RoundImageDrawable) {
                        // just return if it's already a RoundedDrawable
                        return drawable
                    } else if (drawable is LayerDrawable) {
                        val ld = drawable as LayerDrawable?
                        val num = ld!!.numberOfLayers

                        // loop through layers to and change to RoundedDrawables if possible
                        for (i in 0 until num) {
                            val d = ld.getDrawable(i)
                            ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d, radius))
                        }
                        return ld
                    }

                    // try to get a bitmap from the drawable and
                    val bm = drawableToBitmap(drawable)
                    if (bm != null) {
                        return RoundImageDrawable(bm, radius)
                    }
                }
                return drawable
            }

            private fun drawableToBitmap(drawable: Drawable): Bitmap? {
                if (drawable is BitmapDrawable) {
                    return drawable.bitmap
                }

                var bitmap: Bitmap?
                val width = Math.max(drawable.intrinsicWidth, 2)
                val height = Math.max(drawable.intrinsicHeight, 2)
                try {
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap!!)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    bitmap = null
                }

                return bitmap
            }
        }

        init {
            paint.isAntiAlias = true
            paint.isDither = true
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader

        }

        override fun draw(canvas: Canvas) {
            canvas.drawOval(rectF, paint)
            val x = rectF.left + cornerRadius
            val y = rectF.top + cornerRadius
            //canvas.drawCircle(x, y, cornerRadius, paint)
        }

        override fun onBoundsChange(bounds: Rect) {
            super.onBoundsChange(bounds)
            rectF.set(bounds)
        }

        override fun setAlpha(alpha: Int) {
            if (paint.alpha != alpha) {
                paint.alpha = alpha
                invalidateSelf()
            }
        }

        override fun setColorFilter(cf: ColorFilter?) {
            paint.colorFilter = cf
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }

        fun setAntiAlias(aa: Boolean) {
            paint.isAntiAlias = aa
            invalidateSelf()
        }

        override fun setFilterBitmap(filter: Boolean) {
            paint.isFilterBitmap = filter
            invalidateSelf()
        }

        override fun setDither(dither: Boolean) {
            paint.isDither = dither
            invalidateSelf()
        }

        // need to override the below methods to properly scale the image
        override fun getIntrinsicWidth(): Int {
            return bitmap.width
        }

        override fun getIntrinsicHeight(): Int {
            return bitmap.height
        }

    }
}
