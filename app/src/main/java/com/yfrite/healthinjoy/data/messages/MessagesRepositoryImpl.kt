package com.yfrite.healthinjoy.data.messages

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagesRepositoryImpl @Inject constructor(private val dao: MessagesDao): MessagesRepository {
    override fun getHistory(): LiveData<List<Message>> = dao.getHistory()

    override suspend fun insert(data: Message) {
        dao.insert(data)
    }

    override suspend fun clear() {
        dao.clear()
    }
}