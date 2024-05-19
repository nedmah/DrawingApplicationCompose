package com.example.drawingapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.drawingapplication.ui.theme.bottomPanelBorder
import com.example.drawingapplication.ui.theme.bottomPanelColor

@Composable
fun bottomPanel(
    onColorClick : (Color) -> Unit,
    onThicknessClick: () -> Unit,
    onOpacityClick: () -> Unit,
    onStrokeCapClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconList(
            onColorClick = onColorClick,
            onThicknessClick = onThicknessClick,
            onOpacityClick = onOpacityClick,
            onStrokeCapClick = onStrokeCapClick
        )
    }
}


@Composable
fun IconList(
    onColorClick: (Color) -> Unit,
    onThicknessClick: () -> Unit,
    onOpacityClick: () -> Unit,
    onStrokeCapClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier
            .background(bottomPanelColor, RoundedCornerShape(25.dp))
            .border(BorderStroke(1.dp, bottomPanelBorder), RoundedCornerShape(25.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            IconBox(icon = ImageVector.vectorResource(R.drawable.palette), onClick = { onColorClick(Color.Red) })
        }
        item {
            IconBox(icon = ImageVector.vectorResource(R.drawable.thickness), onClick = { onThicknessClick() })
        }
        item {
            IconBox(icon = ImageVector.vectorResource(R.drawable.opacity), onClick = { onOpacityClick() })
        }
        item {
            IconBox(icon = ImageVector.vectorResource(R.drawable.pen_pencil), onClick = { onStrokeCapClick() })
        }
    }
}

@Composable
fun IconBox(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
    }
}

