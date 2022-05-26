package com.yfrite.healthinjoy.data.trainings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrainingsDao {

    @Insert
    suspend fun insert(data: Training)

    @Query(value = "SELECT * FROM trainings_table")
    fun getAll(): LiveData<List<Training>>

    @Query(value = "SELECT * FROM trainings_table WHERE type = :type")
    fun getByType(type: String): LiveData<List<Training>>

    @Query(value = "SELECT * FROM trainings_table WHERE type = :type AND difficulty = :difficulty")
    fun getByTypeAndDifficulty(type: String, difficulty: Int): LiveData<List<Training>>
}