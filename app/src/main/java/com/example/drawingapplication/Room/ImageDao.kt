package com.example.gorbunovmobdev.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert
    suspend fun insertImage(imageData: ImageData)

    @Query("SELECT * FROM image WHERE id = :id")
    suspend fun getImageById(id: Int): ImageData?
}