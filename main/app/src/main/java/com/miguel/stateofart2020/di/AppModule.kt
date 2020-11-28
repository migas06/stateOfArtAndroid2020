package com.miguel.stateofart2020.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: Application) {

    @Provides
    fun providesApplication() : Application = app

}