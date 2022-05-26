package com.yfrite.healthinjoy.data.notifications

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "notifications_table")
data class Notification (

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name= "notificationId")
        var notificationId: String = "null",

        @ColumnInfo(name = "name")
        var name: String = "null",

        @ColumnInfo(name = "time")
        var time: String = "null",

        @ColumnInfo(name = "description")
        var description: String = "null",
)