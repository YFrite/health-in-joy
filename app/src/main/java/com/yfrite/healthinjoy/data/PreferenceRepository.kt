package com.yfrite.healthinjoy.data

import android.content.SharedPreferences

class PreferenceRepository(private val sharedPreferences: SharedPreferences) {

    var userHeight: Int
        get() = sharedPreferences.getInt("PREFERENCE_USER_HEIGHT", 0)
        set(value) = sharedPreferences.edit().putInt("PREFERENCE_USER_HEIGHT", value).apply()

    var userName: String
        get() = sharedPreferences.getString("PREFERENCE_USER_NAME", "0").toString()
        set(value) = sharedPreferences.edit().putString("PREFERENCE_USER_NAME", value).apply()

    var userSex: Boolean
        get() = sharedPreferences.getBoolean("PREFERENCE_USER_SEX", true)
        set(value) = sharedPreferences.edit().putBoolean("PREFERENCE_USER_SEX", value).apply()
}