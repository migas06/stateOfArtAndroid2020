package com.miguel.stateofart2020.di.shareddi

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.miguel.stateofart2020.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class SharedModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun providesSharedPreferences(context: Application) : SharedPreferencesHelper{
        return SharedPreferencesHelper(context)
    }

    /*
     * Example of how to use qualifier
     *
     * */
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun providesSharedPrefWIthActivity(activity : AppCompatActivity) : SharedPreferencesHelper{
        return SharedPreferencesHelper(activity)
    }
}

const val CONTEXT_APP = "applicationContext"
const val CONTEXT_ACTIVITY = "activityContext"

@Qualifier
annotation class TypeOfContext(val type: String)