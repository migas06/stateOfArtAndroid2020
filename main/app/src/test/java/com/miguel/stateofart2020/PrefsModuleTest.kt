package com.miguel.stateofart2020

import android.app.Application
import com.miguel.stateofart2020.di.shareddi.SharedModule
import com.miguel.stateofart2020.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPref: SharedPreferencesHelper) : SharedModule() {
    override fun providesSharedPreferences(context: Application): SharedPreferencesHelper {
        return mockPref
    }
}