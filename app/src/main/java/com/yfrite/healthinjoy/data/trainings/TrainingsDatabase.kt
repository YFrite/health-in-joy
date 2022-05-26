package com.yfrite.healthinjoy.data.trainings

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Training::class], version = 1, exportSchema = false)
abstract class TrainingsDatabase: RoomDatabase() {

    abstract fun trainingsDao(): TrainingsDao
}