package com.chan.chandummy.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.Log


/**
 * Created by chandra-1765$ on 2019-06-16$.
 */

class RoundedImageView : AppCompatImageView {
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

    /*override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            *//*val halfWidth = (width / 2).toFloat()
            val halfHeight = (height / 2).toFloat()
            val radius = Math.max(halfWidth, halfHeight)
            val path = Path()
            path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW)

            canvas.clipPath(path)*//*

            val halfWidth = (canvas.width / 2).toFloat()
            val halfHeight = (canvas.height / 2).toFloat()
            val radius = Math.max(halfWidth, halfHeight)
            val path = Path()
            path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW)

            val paint = Paint(ANTI_ALIAS_FLAG)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            canvas.drawPath(path, paint)
        }
        super.onDraw(canvas)
    }*/

    /*
     * For default images, the round drawable is not used
     */
    fun setDefaultImageBitmap(image: Bitmap) {
        super.setImageBitmap(image)
    }

    fun setImageBitmapWithFade(bitmap: Bitmap) {
        setImageDrawableWithFade(CircleBgImageView.RoundImageDrawable(bitmap, radius))
    }

    override fun setImageBitmap(image: Bitmap) {
        setImageDrawableWithFade(CircleBgImageView.RoundImageDrawable(image, radius))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        background = RoundBackgroundDrawable(Color.RED)
        super.setImageDrawable(drawable)
        Log.d("ChanLog", "setImageDrawable: ");
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        Log.d("ChanLog", "setImageResource: ");
    }

    fun setImageDrawableWithFade(drawable: Drawable) {
        val currentDrawable = getDrawable()
        if (currentDrawable != null) {
            val arrayDrawable = arrayOfNulls<Drawable>(2)
            arrayDrawable[0] = currentDrawable
            arrayDrawable[1] = drawable
            val transitionDrawable = TransitionDrawable(arrayDrawable)
            transitionDrawable.isCrossFadeEnabled = true
            setImageDrawable(transitionDrawable)
            transitionDrawable.startTransition(200)
        } else {
            setImageDrawable(drawable)
        }
    }

    inner class RoundBackgroundDrawable(var mColor: Int) : Drawable() {

        private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        private var mRect: RectF
        private var mPath: Path

        init {
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
