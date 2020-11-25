package com.miguel.stateofart2020.model.Network

import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalApiService {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalAPIInterface::class.java)

    fun getApiKey(): Single<ApiKey>{
        return api.getApiKey()
    }

    fun getAnimals(key: String) : Single<List<Animal>>{
        return api.getAnimals(key)
    }
}