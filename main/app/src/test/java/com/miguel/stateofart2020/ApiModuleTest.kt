package com.miguel.stateofart2020

import com.miguel.stateofart2020.di.serviceapi.ApiModule
import com.miguel.stateofart2020.model.network.AnimalApiService

class ApiModuleTest (val mockedService: AnimalApiService) : ApiModule(){

    override fun provideAnimalService(): AnimalApiService {
        return mockedService
    }

}