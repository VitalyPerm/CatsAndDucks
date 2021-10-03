package com.vitaly.catsandducks.di

import com.vitaly.catsandducks.model.cat.CatService
import com.vitaly.catsandducks.model.duck.DuckService
import com.vitaly.catsandducks.viewmodel.MainActivityViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun injectCatService(service: CatService)
    fun injectDuckService(service: DuckService)
    fun injectViewModel(viewModel: MainActivityViewModel)

}