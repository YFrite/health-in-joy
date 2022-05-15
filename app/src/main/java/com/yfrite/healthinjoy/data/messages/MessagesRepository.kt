package com.yfrite.healthinjoy.data.messages

import androidx.lifecycle.LiveData

interface MessagesRepository {

    fun getHistory(): LiveData<List<Message>>

    suspend fun insert(data: Message)

    suspend fun clear()
}