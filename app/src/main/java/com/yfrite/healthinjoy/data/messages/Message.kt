package com.yfrite.healthinjoy.data.messages

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_table")
data class Message(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "from")
    var from: Sender = Sender.JOY,

    @ColumnInfo(name = "text")
    var text: String = "error",

    //TODO(MAYBE)
//    @ColumnInfo(name = "time")
//    var time: Date =
){
    enum class Sender{
        JOY,
        USER
    }
}

