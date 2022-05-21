package com.yfrite.healthinjoy.util.android.time

class TimeUtil {

    companion object {

        fun secondsByMinutes(data: Int) = data * 60
        fun secondsByHours(data: Int) = secondsByMinutes(minutesByHours(data))

        fun minutesByHours(data: Int) = data * 60
    }

}