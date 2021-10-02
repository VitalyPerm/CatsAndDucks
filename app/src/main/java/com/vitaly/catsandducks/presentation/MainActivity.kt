package com.vitaly.catsandducks.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vitaly.catsandducks.data.Constants
import com.vitaly.catsandducks.databinding.ActivityMainBinding

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
            viewModel.savedPicUrl?.let { viewModel.loadPic(it, binding.ivPic) }
        }
        if (!viewModel.firstLaunch) {
            viewModel.lastPicUrl?.let { viewModel.loadPic(it, binding.ivPic) }
        }
        binding.btnShowDuck.setOnClickListener { viewModel.loadDuck(binding.ivPic, Constants.DUCK_BASE_URL) }
        binding.btnShowCat.setOnClickListener { viewModel.loadCat(binding.ivPic, Constants.CAT_BASE_URL) }
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