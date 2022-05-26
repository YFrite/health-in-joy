package com.yfrite.healthinjoy.data.trainings

import androidx.lifecycle.LiveData

interface TrainingsRepository {

    fun getAll(): LiveData<List<Training>>

    suspend fun insert(data: Training)

    fun getByType(type: String): LiveData<List<Training>>

    fun getByTypeAndDifficulty(type: String, difficulty: Int): LiveData<List<Training>>
}