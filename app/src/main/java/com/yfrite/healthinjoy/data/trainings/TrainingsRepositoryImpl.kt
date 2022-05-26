package com.yfrite.healthinjoy.data.trainings

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingsRepositoryImpl @Inject constructor(private val dao: TrainingsDao): TrainingsRepository{

    override fun getAll(): LiveData<List<Training>> {
        return dao.getAll()
    }

    override suspend fun insert(data: Training) {
        dao.insert(data)
    }

    override fun getByType(type: String): LiveData<List<Training>> {
        return dao.getByType(type)
    }

    override fun getByTypeAndDifficulty(type: String, difficulty: Int): LiveData<List<Training>> {
        return dao.getByTypeAndDifficulty(type, difficulty)
    }

}