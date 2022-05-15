package com.yfrite.healthinjoy.data.eaten

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eaten_table")
data class Eaten(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "water")
    var water: Int = 0,

    @ColumnInfo(name = "calories")
    var calories: Int = 0,

    @ColumnInfo(name = "fats")
    var fats: Int = 0,

    @ColumnInfo(name = "protein")
    var protein: Int = 0,

    @ColumnInfo(name = "carbohydrates")
    var carbohydrates: Int = 0
)