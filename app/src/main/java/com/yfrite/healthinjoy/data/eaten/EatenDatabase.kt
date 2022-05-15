package com.yfrite.healthinjoy.data.eaten

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Eaten::class], version = 1, exportSchema = false)
abstract class EatenDatabase: RoomDatabase() {

    abstract fun eatenDao(): EatenDao
}