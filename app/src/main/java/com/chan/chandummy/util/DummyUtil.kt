package com.chan.chandummy.util

import android.content.Context

/**
 * Created by chandra-1765$ on 09/12/19$.
 */
fun Float.getPixel(context: Context): Float {
    val metrics = context.resources.displayMetrics
    return (metrics.density * this)
}