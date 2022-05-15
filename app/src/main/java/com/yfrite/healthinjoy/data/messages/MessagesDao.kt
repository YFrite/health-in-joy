package com.yfrite.healthinjoy.data.messages

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessagesDao {

    @Insert
    suspend fun insert(data: Message)

    @Update
    suspend fun update(data: Message)

    @Query(value = "SELECT * FROM messages_table")
    fun getHistory(): LiveData<List<Message>>

    @Query(value = "DELETE FROM messages_table")
    suspend fun clear()
}