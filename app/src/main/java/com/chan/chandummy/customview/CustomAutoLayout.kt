package com.chan.chandummy.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView

/**
 * Created by coolchandrakumar on 13-Jun-2019.
 *
 * This Layout is used for auto adjusting the width
 * Direct LinearLayout child is required which has horizontal orientation
 *
 * Calculation based CustomAutoTextView child availability
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

class CustomAutoLayout : HorizontalScrollView {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

    }

    public override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if(width > 0) {
            measureAndAllocateWidth()
        }
    }

    private fun measureAndAllocateWidth() {
        val directChild = getChildAt(0) as ViewGroup
        val count = directChild.childCount
        val widthArray = IntArray(count) {-1}
        val resultArray = IntArray(count) {-1}
        var totalWidth = 0

        var unAllocatedChildCount = 0
        var allocatedChildCount = 0
        for (i in 0 until count) {
            val child = directChild.getChildAt(i)
            if(child is CustomAutoTextView) {
                widthArray[i] = child.width
                unAllocatedChildCount++
            } else {
                allocatedChildCount++
                resultArray[i] = child.width
            }
            totalWidth += child.width
        }

        if(totalWidth <= width || allocatedChildCount >= count) {
            return
        }

        val allocatedWidth = getAllocatedWidth(resultArray)
        if(allocatedWidth < width) {
            val avgWidth = (width - allocatedWidth) / unAllocatedChildCount
            setCalculatedWidth(directChild, getResultArray(widthArray, resultArray, avgWidth))
        } else {
            resultArray.forEachIndexed { index, childWidth ->
                if(childWidth == -1) {
                    resultArray[index] = 1
                }
            }
            setCalculatedWidth(directChild, resultArray)
        }
    }

    private fun setCalculatedWidth(directChild: ViewGroup, allocatedWidthArray: IntArray) {
        val childCount = directChild.childCount
        for (i in 0 until childCount) {
            val calculatedWidth = allocatedWidthArray[i]
            val child = directChild.getChildAt(i)
            if(calculatedWidth > 0 && child is CustomAutoTextView) {
                val params = child.layoutParams
                params.width = calculatedWidth
                child.layoutParams = params
            }
        }
    }

    private fun getResultArray(widthArray: IntArray, resultArray: IntArray, avgWidth: Int): IntArray {
        var unAllocatedChildCount = 0
        var allocatedChildCount = 0
        widthArray.forEachIndexed { index, childWidth ->
            if(childWidth > 0) {
                if(childWidth <= avgWidth) {
                    resultArray[index] = childWidth
                    widthArray[index] = -1
                    allocatedChildCount++
                } else {
                    unAllocatedChildCount++
                }
            }
        }
        return if(allocatedChildCount == 0) {
            widthArray.forEachIndexed { index, i ->
                if(i > avgWidth) {
                    resultArray[index] = avgWidth
                }
            }
            resultArray
        } else {
            val newAvgWidth = (width - getAllocatedWidth(resultArray)) / unAllocatedChildCount
            getResultArray(widthArray, resultArray, newAvgWidth)
        }
    }

    private fun getAllocatedWidth(resultArray: IntArray): Int = resultArray.filter { it > 0 }.sum()

}