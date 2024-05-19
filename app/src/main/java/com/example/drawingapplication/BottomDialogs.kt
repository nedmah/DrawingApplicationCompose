package com.example.drawingapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawingapplication.ui.theme.bottomPanelBorder
import com.example.drawingapplication.ui.theme.bottomPanelColor

@Composable
fun BottomDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun ColorPaletteDialog(onSelect: (Color) -> Unit, onDismiss: () -> Unit) {
    BottomDialog(
        showDialog = true,
        onDismiss = onDismiss
    ) {
        val colors = listOf(
            Color.Black,
            Color.White,
            Color.Red,
            Color.Blue,
            Color.Green,
            Color.Yellow,
            Color.Cyan,
            Color.Gray,
            Color.Magenta
        )

        LazyRow(
            modifier = Modifier
                .background(bottomPanelColor, RoundedCornerShape(25.dp))
                .border(BorderStroke(1.dp, bottomPanelBorder), RoundedCornerShape(25.dp))
                .fillMaxWidth()
        ) {
            items(colors){
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color = it)
                        .clickable {
                            onSelect(it)
                            onDismiss()
                        }
                )
            }
        }
    }
}

@Composable
fun ThicknessDialog(onChange: (Float) -> Unit, onDismiss: () -> Unit) {
    BottomDialog(
        showDialog = true,
        onDismiss = onDismiss
    ) {
        var position by remember {
            mutableStateOf(0.05f)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Thickness : ${(position * 100).toInt()}",
                style = TextStyle(fontSize = 20.sp)
            )
                Slider(
                    value = position,
                    onValueChange = {
                        val tempPos = if (it > 0) it else 0.01f
                        position = tempPos
                        onChange(tempPos * 100)

                    },
                    onValueChangeFinished = {onDismiss()}
                )
        }
    }
}

@Composable
fun OpacityDialog(onChange: (Float) -> Unit, onDismiss: () -> Unit) {
    BottomDialog(
        showDialog = true,
        onDismiss = onDismiss
    ) {
        var position by remember {
            mutableStateOf(1f)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Opacity : ${(position * 100).toInt()}",
                style = TextStyle(fontSize = 20.sp)
            )
            Slider(
                value = position,
                onValueChange = {
                    val tempPos = if (it > 0) it else 0.01f
                    position = tempPos
                    onChange((tempPos))

                },
                onValueChangeFinished = {onDismiss()}
            )
        }
    }
}

@Composable
fun StrokeCapDialog(onSelect: (StrokeCap) -> Unit, onDismiss: () -> Unit) {
    BottomDialog(
        showDialog = true,
        onDismiss = onDismiss
    ) {
        // Example opacity options
        Column {
            Text(
                "Select Cap",
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                listOf(StrokeCap.Butt, StrokeCap.Round, StrokeCap.Square).forEach {
                    Text(style = TextStyle(fontSize = 18.sp),
                        text = "$it",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                            onSelect(it)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

