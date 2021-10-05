package com.vitaly.catsandducks.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vitaly.catsandducks.databinding.ActivityMainBinding
import com.vitaly.catsandducks.utils.APP_ACTIVITY
import com.vitaly.catsandducks.utils.loadImage
import com.vitaly.catsandducks.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.initDatabase()
        viewModel.loadData()
        observeViewModel()
        if (viewModel.firstLaunch) {
            binding.ivPic.loadImage(viewModel.savedPicUrl, viewModel.progressBar)
//            viewModel.savedPicUrl?.let { viewModel.loadPic(it, binding.ivPic) }
        }
        binding.btnShowDuck.setOnClickListener { viewModel.loadDuck() }
        binding.btnShowCat.setOnClickListener { viewModel.loadCat() }
        binding.ivPic.setOnClickListener { viewModel.doubleTap() }
        binding.btnSave?.setOnClickListener { viewModel.saveData() }
        binding.btnLikedPics?.setOnClickListener {
            val i = Intent(this, LikedPicsActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.firstLaunch = false
    }

    private fun observeViewModel() {
        viewModel.picture.observe(this, {
            binding.ivPic.loadImage(it, viewModel.progressBar)
        })

        viewModel.likedPics?.observe(this, {
            for (i in it) {
                Log.d("TAG", i.url)
            }
        })
//        viewModel.allPics.observe(this, {
//            for (i in it) {
//                Log.d("URL!", i.url)
//            }
//        })
    }
}