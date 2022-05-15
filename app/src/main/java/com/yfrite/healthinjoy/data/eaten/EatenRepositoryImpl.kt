package com.yfrite.healthinjoy.data.eaten

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EatenRepositoryImpl @Inject constructor(private val dao: EatenDao): EatenRepository {

    override fun getHistory(): LiveData<List<Eaten>> = dao.getHistory()

    override suspend fun insert(data: Eaten) {
        dao.insert(data)
    }

    override suspend fun update(data: Eaten) {
        dao.update(data)
    }

    override suspend fun clear() {
        dao.clear()
    }

}