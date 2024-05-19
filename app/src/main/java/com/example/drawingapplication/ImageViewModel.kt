package com.example.gorbunovmobdev

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.drawingapplication.Room.bitmapToByteArray
import com.example.gorbunovmobdev.data.MainDb
import com.example.gorbunovmobdev.data.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class ImageViewModel(val database: MainDb) : ViewModel(){



    fun saveDrawingToDatabase(fileName: String, image: ByteArray) {
        val imageData = ImageData(
            null,
            name = fileName,
            image = image
        )

        viewModelScope.launch {
            database.dao.insertImage(imageData)
        }
    }



    companion object{
        val factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return ImageViewModel(database) as T
            }
        }
    }

}