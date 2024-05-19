package com.example.drawingapplication.Room

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb
import com.example.drawingapplication.PathData
import java.io.ByteArrayOutputStream

fun canvasToBitmap(pathList: List<PathData>, width: Int = 480, height: Int = 800): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    pathList.forEach { pathData ->
        val paint = Paint().apply {
            color = pathData.color.toArgb()
            alpha = (pathData.alpha * 255).toInt()
            strokeWidth = pathData.strokeWidth
            style = Paint.Style.STROKE
            strokeCap = when (pathData.cap) {
                StrokeCap.Butt -> Paint.Cap.BUTT
                StrokeCap.Round -> Paint.Cap.ROUND
                StrokeCap.Square -> Paint.Cap.SQUARE
                else -> null
            }
        }
        canvas.drawPath(pathData.path.asAndroidPath(), paint)
    }
    return bitmap
}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}