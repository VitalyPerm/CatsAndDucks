package com.vitaly.catsandducks.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.model.db.LikedPicture

class LikedPicsAdapter() : RecyclerView.Adapter<LikedPicsAdapter.LikedPicsViewHolder>() {
    private var mListPics = emptyList<LikedPicture>()

    inner class LikedPicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val url: TextView = view.findViewById(R.id.url)
        val pic: ImageView = view.findViewById(R.id.pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedPicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_liked_pics, parent, false)
        return LikedPicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikedPicsViewHolder, position: Int) {
        holder.url.text = holder.adapterPosition.toString()
        Glide
            .with(holder.pic)
            .load(mListPics[position].url)
            .into(holder.pic)
    }

    override fun getItemCount(): Int = mListPics.size

    fun setList(list: List<LikedPicture>) {
        mListPics = list.asReversed()
        notifyDataSetChanged()
    }
}