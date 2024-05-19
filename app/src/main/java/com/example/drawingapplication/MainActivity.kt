package com.example.drawingapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drawingapplication.Room.bitmapToByteArray
import com.example.drawingapplication.Room.canvasToBitmap
import com.example.gorbunovmobdev.ImageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val pathData = remember { mutableStateOf(PathData()) }
            val pathList = remember { mutableStateListOf(PathData()) }

            val showThicknessDialog = remember { mutableStateOf(false) }
            val showOpacityDialog = remember { mutableStateOf(false) }
            val showColorDialog = remember { mutableStateOf(false) }
            val showStrokeCapDialog = remember { mutableStateOf(false) }
            val showSaveDialog = remember { mutableStateOf(false) }

            val imageViewModel : ImageViewModel = viewModel(factory = ImageViewModel.factory)

            fun undoLastAction() {
                if (pathList.isNotEmpty()) {
                    pathList.removeIf{
                        it == pathList[pathList.lastIndex]
                    }
                }
            }

            fun saveDrawing(fileName: String) {
                val bitmap = canvasToBitmap(pathList)
                val byteArray = bitmapToByteArray(bitmap)
                imageViewModel.saveDrawingToDatabase(fileName,byteArray)
            }


            Column {
                TopPanel(
                    onBackClick = { undoLastAction() },
                    onDownloadClick = {showSaveDialog.value = true}
                )
                MainCanvas(pathData, pathList)
                bottomPanel(
                    onColorClick = { showColorDialog.value = true },
                    onThicknessClick = { showThicknessDialog.value = true },
                    onOpacityClick = { showOpacityDialog.value = true },
                    onStrokeCapClick = { showStrokeCapDialog.value = true }
                )
            }

            if (showColorDialog.value) {
                ColorPaletteDialog(
                    onSelect = { color -> pathData.value = pathData.value.copy(color = color) },
                    onDismiss = { showColorDialog.value = false }
                )

            }

            if (showThicknessDialog.value) {
                ThicknessDialog(
                    onChange = { thickness ->
                        pathData.value = pathData.value.copy(strokeWidth = thickness)
                    },
                    onDismiss = { showThicknessDialog.value = false }
                )
            }

            if (showOpacityDialog.value) {
                OpacityDialog(
                    onChange = { opacity -> pathData.value = pathData.value.copy(alpha = opacity) },
                    onDismiss = { showOpacityDialog.value = false }
                )
            }
            if (showStrokeCapDialog.value) {
                StrokeCapDialog(
                    onSelect = { cap -> pathData.value = pathData.value.copy(cap = cap) },
                    onDismiss = { showStrokeCapDialog.value = false }
                )
            }

            if (showSaveDialog.value) {
                SaveDialog(
                    onDismiss = { showSaveDialog.value = false },
                    onSave = { fileName -> saveDrawing(fileName) }
                )
            }


        }
    }
}


@Composable
fun MainCanvas(pathData: MutableState<PathData>, pathList: SnapshotStateList<PathData>) {

    var tempPath = Path()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        tempPath = Path()
                    },
                    onDragEnd = {
                        pathList.add(
                            pathData.value.copy(path = tempPath)
                        )
                    }
                ) { change, dragAmount ->
                    tempPath.moveTo(
                        change.position.x - dragAmount.x,
                        change.position.y - dragAmount.y
                    )
                    tempPath.lineTo(
                        change.position.x,
                        change.position.y
                    )

                    if (pathList.size > 0) pathList.removeAt(pathList.size - 1)

                    pathList.add(
                        pathData.value.copy(path = tempPath)
                    )

                }
            }
    ) {
        pathList.forEach {
            drawPath(
                it.path,
                color = it.color,
                alpha = it.alpha,
                style = Stroke(
                    width = it.strokeWidth,
                    cap = it.cap
                )
            )
        }
    }
}

@Composable
fun TopPanel(onBackClick : () -> Unit,
             onDownloadClick : () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.back),
            contentDescription = null,
            Modifier
                .padding(5.dp)
                .clickable {
                    onBackClick()
            }
        )
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.download),
            contentDescription = null,
            Modifier
                .padding(5.dp)
                .clickable {
                    onDownloadClick()
            }
        )
    }
}