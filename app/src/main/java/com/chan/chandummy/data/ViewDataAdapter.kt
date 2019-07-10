package com.chan.chandummy.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chan.chandummy.R
import com.chan.chandummy.customview.CustomAutoTextView

/**
 * Created by chandra-1765$ on 2019-07-09$.
 */
data class ViewDataAdapter(val dataArray: ArrayList<String>) : RecyclerView.Adapter<ViewDataAdapter.CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataAdapter.CustomHolder {
        return CustomHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_data_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewDataAdapter.CustomHolder, position: Int) {
        //408
        //263
        //408
        /*holder.itemView.findViewById<CustomAutoLayout>(R.id.customAutoLayout)?.let { customAutoLayout ->
            customAutoLayout.viewTreeObserver.addOnGlobalLayoutListener {
                customAutoLayout.measureAndAllocateWidth()
            }
        }*/

        holder.itemView.findViewById<CustomAutoTextView>(R.id.firstName).text = "afterHeight"
        /*holder.itemView.findViewById<CustomAutoTextView>(R.id.firstName).refresh()
        holder.itemView.findViewById<CustomAutoTextView>(R.id.lastName).refresh()
        holder.itemView.findViewById<CustomAutoTextView>(R.id.thirdName).refresh()
        holder.itemView.findViewById<CustomAutoTextView>(R.id.firstName).layoutParams.width = 408
        holder.itemView.findViewById<CustomAutoTextView>(R.id.lastName).layoutParams.width = 263
        holder.itemView.findViewById<CustomAutoTextView>(R.id.thirdName).layoutParams.width = 408*/
        //holder.itemView.requestLayout()

    }

    override fun getItemCount(): Int = dataArray.size

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view)

}