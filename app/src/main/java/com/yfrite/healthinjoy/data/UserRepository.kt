package com.yfrite.healthinjoy.data

import android.content.SharedPreferences

class UserRepository(private val sharedPreferences: SharedPreferences) {

    var name: String
        get() = sharedPreferences.getString("name", "0").toString()
        set(value) = sharedPreferences.edit().putString("name", value).apply()

    var weight: Int
        get() = sharedPreferences.getInt("weight", 0)
        set(value) = sharedPreferences.edit().putInt("weight", value).apply()

    var height: Int
        get() = sharedPreferences.getInt("height", 0)
        set(value) = sharedPreferences.edit().putInt("height", value).apply()

    var sex: Int
        get() = sharedPreferences.getInt("sex", -1)
        set(value) = sharedPreferences.edit().putInt("sex", value).apply()

    var lifestyle: Int
        get() = sharedPreferences.getInt("lifestyle", 0)
        set(value) = sharedPreferences.edit().putInt("lifestyle", value).apply()

    var droppedCalories: Int
        get() = sharedPreferences.getInt("dropped_calories", 0)
        set(value) = sharedPreferences.edit().putInt("dropped_calories", value).apply()
}