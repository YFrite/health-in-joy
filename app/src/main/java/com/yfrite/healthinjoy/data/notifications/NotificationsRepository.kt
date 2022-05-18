package com.yfrite.healthinjoy.data.notifications

import androidx.lifecycle.LiveData
import com.yfrite.healthinjoy.data.notifications.Notification
import javax.inject.Singleton

interface NotificationsRepository {

    fun getAll(): LiveData<List<Notification>>

    suspend fun insert(data: Notification)

    suspend fun getTwoNotifications(): LiveData<List<Notification>>

    suspend fun delete(id: Long)

    suspend fun clear()
}