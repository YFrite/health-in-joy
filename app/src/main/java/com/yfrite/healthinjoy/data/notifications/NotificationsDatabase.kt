package com.yfrite.healthinjoy.data.notifications

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notification::class], version = 1, exportSchema = false)
abstract class NotificationsDatabase: RoomDatabase() {

    abstract fun notificationsDao(): NotificationsDao
}