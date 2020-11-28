package com.miguel.stateofart2020.di.serviceapi

import com.miguel.stateofart2020.di.AppModule
import com.miguel.stateofart2020.di.serviceapi.ApiModule
import com.miguel.stateofart2020.di.shareddi.SharedModule
import com.miguel.stateofart2020.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton //sharedModule uses, so Component uses it to
@Component(modules = [ApiModule::class, AppModule::class, SharedModule::class])
interface ViewModelListComponent {

    fun inject(viewModel: ListViewModel)

}