package com.example.drawingapplication

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap

data class PathData(
    val path : Path = Path(),
    val color: Color = Color.Black,
    val strokeWidth: Float = 5f,
    val alpha: Float = 1f,
    val cap : StrokeCap = StrokeCap.Round
)
