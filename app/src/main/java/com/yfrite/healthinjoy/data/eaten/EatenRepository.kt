package com.yfrite.healthinjoy.data.eaten

import androidx.lifecycle.LiveData


interface EatenRepository {

    fun getHistory(): LiveData<List<Eaten>>

    suspend fun insert(data: Eaten)

    suspend fun update(data: Eaten)

    suspend fun clear()
}
