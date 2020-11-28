package com.miguel.stateofart2020.di

import com.miguel.stateofart2020.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelListComponent {

    fun inject(viewModel: ListViewModel)

}