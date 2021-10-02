package com.vitaly.catsandducks.presentation

import android.os.Bundle
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
        if(!viewModel.lastPicUrl.isNullOrEmpty()){
           viewModel.loadPic(viewModel.lastPicUrl!!, binding.ivPic)
        }
        binding.btnShowDuck.setOnClickListener {
            viewModel.loadDuck(binding.ivPic, Constants.DUCK_BASE_URL)
        }

        binding.btnShowCat.setOnClickListener {
            viewModel.loadCat(binding.ivPic, Constants.CAT_BASE_URL)
        }
        binding.ivPic.setOnClickListener {
            viewModel.doubleTap()
        }
    }
}