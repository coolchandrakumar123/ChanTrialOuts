package com.chan.chandummy.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.chan.chandummy.R


/**
 * Created by chandra-1765$ on 09/12/19$.
 */
fun Float.getPixel(context: Context): Float {
    val metrics = context.resources.displayMetrics
    return (metrics.density * this)
}

fun getBlurCircle(context: Context, @ColorRes colors: IntArray): Drawable {
    val colorsArray = colors.map { ContextCompat.getColor(context, it) }.toIntArray()
    val positiveShaderFactory = object : ShapeDrawable.ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return RadialGradient(width.toFloat()/2, height.toFloat()/2,
                    width.toFloat()/2, colorsArray, floatArrayOf(0.2f, 1.2f), Shader.TileMode.CLAMP)
        }
    }
    val positivePaintDrawable = PaintDrawable()
    positivePaintDrawable.shape = OvalShape()
    positivePaintDrawable.shaderFactory = positiveShaderFactory
    val positiveLayers = arrayOf<Drawable>(positivePaintDrawable)
    return LayerDrawable(positiveLayers)
}