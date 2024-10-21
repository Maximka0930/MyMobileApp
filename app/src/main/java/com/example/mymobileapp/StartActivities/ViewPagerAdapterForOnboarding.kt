package com.example.mymobileapp.StartActivities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymobileapp.R

class ViewPagerAdapterForOnboarding(
    val images: List<Int>
) : RecyclerView.Adapter<ViewPagerAdapterForOnboarding.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val curImage = images[position]
        val imageView = holder.itemView.findViewById<ImageView>(R.id.ivImage) // исправлено
        imageView.setImageResource(curImage) // исправлено
    }


    override fun getItemCount(): Int {
        return images.size
    }
}