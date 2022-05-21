package com.yfrite.healthinjoy.util.network

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

@DelicateCoroutinesApi
class WeatherAPI {

    //private val YANDEXAPIKEY = "9e8e2fc4-5970-4ca6-95c5-3e620095e8e3"

    companion object {
        private val client = OkHttpClient()

        fun getWeather(
            lat: String,
            lon: String,
            exclude: String,
            lang: String,
            units: String
        ): ArrayList<String> {
            val infoList = ArrayList<String>()

            val url =
                "https://api.openweathermap.org/data/2.5/onecall?appid=ef579925a8bef98928577e47621e42c7" +
                        "&lat=" + lat +
                        "&lon=" + lon +
                        "&exclude=" + exclude +
                        "&lang=" + lang +
                        "&units=" + units

            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val answer = JSONObject(response.body!!.string()).getJSONObject("current")

                val weather = answer.getJSONArray("weather").getJSONObject(0)

                infoList.add(answer.getString("temp"))
                infoList.add(weather.getString("description"))
                //infoList.add(answer.getString("humidity"))
                //infoList.add(answer.getString("pressure"))
                //infoList.add(answer.getString("wind_speed"))
                infoList.add(answer.getString("feels_like"))
                infoList.add(getWeatherIcon(weather.getString("icon")))
                //infoList.add(answer.getString("sunrise"))
                //infoList.add(answer.getString("sunset"))
                Log.e("List info", answer.toString())

            }
            return infoList
        }

        private fun getWeatherIcon(code: String): String = "https://openweathermap.org/img/wn/${code}@2x.png"

    }
}