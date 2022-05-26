package com.yfrite.healthinjoy.data.trainings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings_table")
data class Training(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "type")
    var type: String = "null",

    @ColumnInfo(name = "difficulty")
    var difficulty: Int = -1,

    @ColumnInfo(name = "name")
    var name: String = "null",

    @ColumnInfo(name = "description")
    var description: String = "null",

    @ColumnInfo(name = "time")
    var time: Int = -1,

    @ColumnInfo(name = "imageId")
    var imageId: Long = -1L,
)
