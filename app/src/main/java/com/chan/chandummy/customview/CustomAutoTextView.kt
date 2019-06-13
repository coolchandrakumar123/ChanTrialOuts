package com.chan.chandummy.customview

import android.content.Context
import android.support.design.R
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by coolchandrakumar on 13-Jun-2019.
 *
 * This ChildView is used for auto adjusting the width
 *
 * It has to be set under the LinearLayout parent which has CustomAutoLayout as a parent
 * CustomAutoLayout calcualte and assign width to it
 * It supports only wrap content
 *
 * <com.zoho.desk.radar.ticket.detailview.customview.CustomAutoLayout
 *      android:id="@+id/auto_layout"
 *      android:layout_width="match_parent"
 *      android:layout_height="wrap_content">
 *  <LinearLayout
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content"
 *      android:layout_gravity="start"
 *      android:gravity="start"
 *      android:orientation="horizontal">
 *      <com.zoho.desk.radar.ticket.detailview.customview.CustomAutoTextView
 *          android:id="@+id/child1"
 *          android:layout_width="wrap_content"
 *          android:layout_height="wrap_content"
 *          android:ellipsize="end"
 *          android:maxLines="1"
 *          android:paddingStart="0dp"
 *          android:paddingEnd="8dp" />
 *  </LinearLayout>
 * </com.zoho.desk.radar.ticket.detailview.customview.CustomAutoLayout>
 *
 */

class CustomAutoTextView : TextView {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }
    var isAutoWidthEnabled = false
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val customStyle = context.obtainStyledAttributes(attrs, com.chan.chandummy.R.styleable.CustomAutoTextView, defStyleAttr, R.style.Widget_Design_TabLayout)
        isAutoWidthEnabled = customStyle.getBoolean(com.chan.chandummy.R.styleable.CustomAutoTextView_autoWidthEnabled, false)
        customStyle.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if(isAutoWidthEnabled) {
            val params = this.layoutParams
            params?.let {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                this.layoutParams = params
            }
        }
        super.setText(text, type)
    }

}