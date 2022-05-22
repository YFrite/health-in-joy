package com.yfrite.healthinjoy.data.notifications

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepositoryImpl @Inject constructor(private val dao: NotificationsDao): NotificationsRepository{
    override fun getAll(): LiveData<List<Notification>> = dao.getAll()

    override suspend fun insert(data: Notification) {
        dao.insert(data)
    }

    override fun getTwoNotifications(): LiveData<List<Notification>> {
        return dao.getTwoNotifications()
    }

    override suspend fun clear() {
        dao.clear()
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }
}