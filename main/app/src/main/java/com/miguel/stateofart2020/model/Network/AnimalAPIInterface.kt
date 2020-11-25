package com.miguel.stateofart2020.model.Network

import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalAPIInterface {

    @GET("getKey")
    fun getApiKey() : Single<ApiKey> //Single is like an observable that receives a response or an error
                                    //that means recieve a "single" answer

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String) : Single<List<Animal>>
}