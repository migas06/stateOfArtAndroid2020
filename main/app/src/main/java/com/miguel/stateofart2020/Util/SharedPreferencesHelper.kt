package com.miguel.stateofart2020.Util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesHelper(context: Context){

    private val PREF_API_KEY = "API keu";

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveAPIKey(key: String?){
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

    fun getApiKey() = prefs.getString(PREF_API_KEY, null)

}