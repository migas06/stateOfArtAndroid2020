package com.miguel.stateofart2020.di

import com.miguel.stateofart2020.model.network.AnimalApiService
import dagger.Component

@Component( modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AnimalApiService)

}