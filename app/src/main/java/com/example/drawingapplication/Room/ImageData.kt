package com.example.gorbunovmobdev.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "image")
data class ImageData(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val name : String,
    val image : ByteArray

)
