package com.yfrite.healthinjoy.util.ai

import android.util.Log
import com.yfrite.healthinjoy.data.messages.Message
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class GPT3 {

    companion object {
        private val client = OkHttpClient.Builder()
            .callTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        private const val COMPLETION_URL =
            "https://api.openai.com/v1/engines/text-davinci-002/completions"

        private const val PROMPT = "Следующее это диалог человека с русским ИИ. ИИ полезный, умный и дружелюбный. "

        private const val MESSAGES_COUNT = 10

        private const val API_KEY_OPENAI = "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"

        private const val USER_NAME = "Человек: "
        private const val JOY_NAME = "Джой: "

        private const val TEMPERATURE = 0.9
        private const val MAX_TOKENS = 1000
        private const val TOP_P = 1
        private const val FREQUENCY_PENALTY = 0.5
        private const val PRESENCE_PENALTY = 0.7
        private const val STOP = "[\" $USER_NAME\", \" $JOY_NAME\"]"
    }

    fun answer(history: List<Message>): String {

        var prompt = ""
        prompt = if (history.size <= 5) {
            prepareText(history.subList(0, history.size))
        } else {
            prepareText(history.subList(0, 1) + history.subList(history.size - MESSAGES_COUNT - 1, history.size - 1))
        }

        val request = Request.Builder()
            .url(COMPLETION_URL)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $API_KEY_OPENAI")
            .post(
                ("{\n" +
                        "\"prompt\": \"$prompt\",\n" +
                        "\"temperature\": $TEMPERATURE,\n" +
                        "\"max_tokens\": $MAX_TOKENS,\n" +
                        "\"top_p\": $TOP_P," +
                        "\"frequency_penalty\": $FREQUENCY_PENALTY,\n" +
                        "\"presence_penalty\": $PRESENCE_PENALTY,\n" +
                        "\"stop\": $STOP\n" +
                        "}").toRequestBody()
            )

        return try {
            val result = client
                .newCall(request.build()).execute().body?.string()
            Log.i("GPT-3 answer", result!!)

            val answer = JSONObject(result)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getString("text")

            answer
        } catch (error: Exception) {
            Log.e("GPT-3", error.stackTraceToString())
            error.stackTraceToString()
        }
    }

    private fun prepareText(history: List<Message>): String {
        val body = StringBuilder(PROMPT)

        for(message in history){
            if(message.from == Message.Sender.USER){
                body.append("$USER_NAME: ${message.text.replace("\n", " ")} ")
            }
            else{
                body.append("$JOY_NAME: ${message.text.replace("\n", " ")} ")
            }
        }
        body.append("$JOY_NAME: ")

        Log.e("Prompt", body.toString())

        return body.toString()
    }

}