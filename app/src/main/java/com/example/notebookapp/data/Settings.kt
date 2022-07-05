package com.example.notebookapp.data

import android.content.Context
import android.content.SharedPreferences

typealias ShPrefListener = SharedPreferences.OnSharedPreferenceChangeListener

class Settings(context: Context) {

    private var preferences: SharedPreferences
    private lateinit var prefListener : ShPrefListener

    var prefText: String?
        get() = preferences.getString(APP_PREFERENCES, "")
        set(value) {
            preferences.edit()
                .putString(PREF_TEXT_VALUE, value)
                .apply()
        }

    init {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setListener(action: Unit, key: String) {
        prefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, sKey -> action }
        preferences.registerOnSharedPreferenceChangeListener(prefListener)
    }

    fun unregisterListener() {
        preferences.unregisterOnSharedPreferenceChangeListener(prefListener)
    }

    companion object {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val PREF_TEXT_VALUE = "PREF_TEXT_VALUE"
    }
}