package com.vitaly.catsandducks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vitaly.catsandducks.R
import com.vitaly.catsandducks.model.db.LikedPicture

class LikedPicsAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<LikedPicsAdapter.LikedPicsViewHolder>() {
     var mListPics = emptyList<LikedPicture>()

    inner class LikedPicsViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val pic: ImageView = view.findViewById(R.id.pic)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) listener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedPicsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_liked_pics, parent, false)
        return LikedPicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikedPicsViewHolder, position: Int) {
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



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}