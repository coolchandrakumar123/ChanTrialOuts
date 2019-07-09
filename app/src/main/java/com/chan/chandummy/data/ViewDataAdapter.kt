package com.chan.chandummy.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chan.chandummy.R

/**
 * Created by chandra-1765$ on 2019-07-09$.
 */
data class ViewDataAdapter(val dataArray: ArrayList<String>) : RecyclerView.Adapter<ViewDataAdapter.CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataAdapter.CustomHolder {
        return CustomHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_data_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewDataAdapter.CustomHolder, position: Int) {

    }

    override fun getItemCount(): Int = dataArray.size

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view)

}