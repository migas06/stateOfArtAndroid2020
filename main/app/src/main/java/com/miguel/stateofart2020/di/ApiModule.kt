package com.miguel.stateofart2020.di

import com.miguel.stateofart2020.model.network.AnimalAPIInterface
import com.miguel.stateofart2020.model.network.AnimalApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net/"

    @Provides
    fun provideAnimalApi(): AnimalAPIInterface {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalAPIInterface::class.java)
    }

    @Provides
    fun provideAnimalService() : AnimalApiService{
        return AnimalApiService()
    }

}