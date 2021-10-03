package com.vitaly.catsandducks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vitaly.catsandducks.utils.Constants
import com.vitaly.catsandducks.databinding.ActivityMainBinding
import com.vitaly.catsandducks.utils.loadImage
import com.vitaly.catsandducks.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.loadData()
        if (viewModel.firstLaunch) {
            binding.ivPic.loadImage(viewModel.savedPicUrl, viewModel.progressBar)
            viewModel.savedPicUrl?.let { viewModel.loadPic(it, binding.ivPic) }
        }
        if (!viewModel.firstLaunch) {
            binding.ivPic.loadImage(viewModel.lastPicUrl, viewModel.progressBar)
        }
        binding.btnShowDuck.setOnClickListener { viewModel.loadDuck(binding.ivPic) }
        binding.btnShowCat.setOnClickListener { viewModel.loadCat(binding.ivPic) }
        binding.ivPic.setOnClickListener { viewModel.doubleTap() }
        binding.btnSave?.setOnClickListener { viewModel.saveData() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.firstLaunch = false
    }


    companion object {
        const val SHARED_PREFS_KEY = "shared_prefs_key"
        const val URL = "url"
    }
}