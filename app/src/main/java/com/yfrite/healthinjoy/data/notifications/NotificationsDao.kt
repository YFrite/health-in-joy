package com.yfrite.healthinjoy.data.notifications

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yfrite.healthinjoy.data.messages.Message

@Dao
interface NotificationsDao {

    @Insert
    suspend fun insert(data: Notification)

    @Update
    suspend fun update(data: Notification)

    @Query(value = "DELETE FROM notifications_table WHERE id=:id")
    fun delete(id: Long)

    @Query(value = "SELECT * FROM notifications_table")
    fun getAll(): LiveData<List<Notification>>

    @Query(value = "SELECT * FROM notifications_table LIMIT 2")
    fun getTwoNotifications(): LiveData<List<Notification>>

    @Query(value = "DELETE FROM notifications_table")
    suspend fun clear()
}