package com.chan.chandummy.customview

/*import android.R.attr.centerY
import android.R.attr.centerX
import android.content.Context
import android.view.View.MeasureSpec
import android.graphics.PixelFormat
import android.graphics.ColorFilter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.annotation.NonNull
import android.graphics.drawable.Drawable
import sun.security.krb5.internal.KDCOptions.with
import android.icu.lang.UScript.getShortName
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User
import android.support.v4.content.ContextCompat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import java.awt.font.ShapeGraphicAttribute.STROKE
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.TextPaint
import android.graphics.RectF
import android.support.v4.view.ViewCompat.setLayerType
import android.os.Build
import com.chan.chandummy.customview.AvatarView
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.chan.chandummy.R*/


/**
 * Created by chandra-1765$ on 2019-06-17$.
 */

/*
class AvatarView : AppCompatImageView {

    */
/*
    * Path of them image to be clipped (to be shown)
    * *//*

    internal var clipPath: Path

    */
/*
    * Place holder drawable (with background color and initials)
    * *//*

    internal var drawable: Drawable

    */
/*
    * Contains initials of the member
    * *//*

    internal var text: String? = null

    */
/*
    * Used to set size and color of the member initials
    * *//*

    internal var textPaint: TextPaint

    */
/*
    * Used as background of the initials with user specific color
    * *//*

    internal var paint: Paint

    */
/*
    * To draw border
    *//*

    private var borderPaint: Paint? = null

    */
/*
    * Shape to be drawn
    * *//*

    internal var shape: Int = 0

    */
/*
    * User whose avatar should be displayed
    * *//*

    //internal var user: User

    */
/*
    * Image width and height (both are same and constant, defined in dimens.xml
    * We cache them in this field
    * *//*

    private var imageSize: Int = 0

    */
/*
    * We will set it as 2dp
    * *//*

    internal var cornerRadius: Float = 0

    */
/*
    * Bounds of the canvas in float
    * Used to set bounds of member initial and background
    * *//*

    internal var rectF: RectF

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        getAttributes(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        getAttributes(attrs)
        init()
    }

    private fun getAttributes(attrs: AttributeSet) {
        */
/*val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.AvatarView,
                0, 0)

        try {

            *//*
*/
/*
            * Get the shape and set shape field accordingly
            * *//*
*/
/*
            val avatarShape = a.getString(R.styleable.AvatarView_avatar_shape)

            *//*
*/
/*
            * If the attribute is not specified, consider circle shape
            * *//*
*/
/*
            if (avatarShape == null) {
                shape = CIRCLE
            } else {
                if (context.getString(R.string.rectangle) == avatarShape) {
                    shape = RECTANGLE
                } else {
                    shape = CIRCLE
                }
            }
        } finally {
            a.recycle()
        }*//*

    }

    */
/*
    * Initialize fields
    * *//*

    protected fun init() {

        */
/*
        * Below Jelly Bean, clipPath on canvas would not work because lack of hardware acceleration
        * support. Hence, we should explicitly say to use software acceleration.
        * *//*

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        rectF = RectF()
        clipPath = Path()

        //imageSize = resources.getDimensionPixelSize(R.dimen.avatar_size)
        //cornerRadius = Utils.dpToPixel(2, resources)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = 16f * resources.displayMetrics.scaledDensity
        textPaint.color = Color.WHITE

        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint!!.setStyle(Paint.Style.STROKE)
        borderPaint!!.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OVER))
        //borderPaint!!.setColor(ContextCompat.getColor(context, R.color.border_color))
        //borderPaint!!.setStrokeWidth(context.resources.getDimension(R.dimen.border_width))
    }

    */
/*
    * Get User object and set values based on the user
    * This is the only exposed method to the developer
    * *//*

    */
/*fun setUser(user: User) {
        this.user = user
        setValues()
    }*//*


    */
/*
    * Set user specific fields in here
    * *//*

    private fun setValues() {

        */
/*
        * user specific color for initial background
        * *//*

        paint.setColor(user.getColor())

        */
/*
        * Initials of member
        * *//*

        //text = Helper.getShortName(user.getName())

        setDrawable()

        if (user.getAvatarUrl() != null) {
            */
/*Glide.with(context)
                    .load(user.getAvatarUrl())
                    .placeholder(drawable)
                    .centerCrop()
                    .override(imageSize, imageSize)
                    .into(this)*//*

        } else {
            setImageDrawable(drawable)
            invalidate()
        }
    }


    */
/*
    * Create placeholder drawable
    * *//*

    private fun setDrawable() {
        drawable = object : Drawable() {
            override fun draw(canvas: Canvas) {

                val centerX = Math.round(canvas.getWidth() * 0.5f).toFloat()
                val centerY = Math.round(canvas.getHeight() * 0.5f).toFloat()

                */
/*
        * To draw text
        * *//*

                if (text != null) {
                    val textWidth = textPaint.measureText(text) * 0.5f
                    val textBaseLineHeight = textPaint.fontMetrics.ascent * -0.4f

                    */
/*
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

                    */
/*
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
    }

    */
/*
    * Set the canvas bounds here
    * *//*

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        val screenHeight = MeasureSpec.getSize(heightMeasureSpec)
        rectF.set(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat())
    }

    protected fun onDraw(canvas: Canvas) {

        if (shape == RECTANGLE) {
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, borderPaint)
            clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        } else {
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.height() / 2 - borderWidth, borderPaint)

            clipPath.addCircle(rectF.centerX(), rectF.centerY(), rectF.height() / 2, Path.Direction.CW)
        }
        canvas.clipPath(clipPath)
        super.onDraw(canvas)
    }

    companion object {

        */
/*
    * Constants to define shape
    * *//*

        protected val CIRCLE = 0
        protected val RECTANGLE = 1
    }
}*/
