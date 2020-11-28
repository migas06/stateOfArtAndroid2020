package com.miguel.stateofart2020.model.network

import com.miguel.stateofart2020.di.DaggerApiComponent
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import io.reactivex.Single
import javax.inject.Inject

class AnimalApiService {

    @Inject
    lateinit var api : AnimalAPIInterface

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey>{
        return api.getApiKey()
    }

    fun getAnimals(key: String) : Single<List<Animal>>{
        return api.getAnimals(key)
    }
}