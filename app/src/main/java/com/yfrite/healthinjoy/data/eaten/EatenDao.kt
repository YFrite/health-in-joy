package com.yfrite.healthinjoy.data.eaten

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EatenDao {

    @Insert
    suspend fun insert(data: Eaten)

    @Update
    suspend fun update(data: Eaten)

    @Query(value = "SELECT * from eaten_table")
    fun getHistory(): LiveData<List<Eaten>>

//    @Query(value = "SELECT * from eaten_table WHERE id=:id")
//    fun last(id: Long): LiveData<Health>

    @Query(value = "DELETE FROM eaten_table")
    suspend fun clear()

}