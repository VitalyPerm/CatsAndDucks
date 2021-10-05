package com.vitaly.catsandducks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vitaly.catsandducks.databinding.ActivityLikedPicsBinding
import com.vitaly.catsandducks.model.db.LikedPicture
import com.vitaly.catsandducks.viewmodel.LikedPicsViewModel

class LikedPicsActivity : AppCompatActivity(), LikedPicsAdapter.OnItemClickListener {
    private lateinit var viewModel: LikedPicsViewModel
    private lateinit var binding: ActivityLikedPicsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LikedPicsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPicsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LikedPicsViewModel::class.java)
        recyclerView = binding.rv
        adapter = LikedPicsAdapter(this)
        recyclerView.adapter = adapter
        viewModel.initDatabase()
        viewModel.likedPics?.observe(this, {
            adapter.setList(it)
        })
    }

    override fun onItemClick(position: Int) {
        viewModel.delete(LikedPicture(id = adapter.mListPics[position].id, url = adapter.mListPics[position].url))
        adapter.notifyItemChanged(position)
    }
}