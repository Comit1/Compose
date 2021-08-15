package com.comit.compose.chart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comit.compose.ui.theme.ComposeTheme

/*
 * Created by Comit on 2021/8/14.
 *
 * references: https://juejin.cn/post/6986544238984953886#heading-0
 */
class LineChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPath()
        setContent {
            ComposeTheme {

//                Surface(color = MaterialTheme.colors.background) {
//                    LineChart()
//                }

                 LineChart()
            }
        }
    }

    @Composable
    private fun LineChart() {
        var scale by remember { mutableStateOf(1f) }
        val state = rememberTransformableState {
            zoomChange, panChange, rotationChange ->
//            Log.d("Comit", "zoomChange: $zoomChange, " +
//                    "panChange: $panChange, " +
//                    "rotationChange: $rotationChange")
            scale *= zoomChange
        }

        var points = listOf(
            Point(10f, 10f), Point(50f, 100f), Point(100f, 30f),
            Point(150f, 200f), Point(200f, 120f), Point(250f, 10f),
            Point(300f, 280f), Point(350f, 100f), Point(400f, 10f),
            Point(450f, 100f), Point(500f, 200f)
        )
        val path1 = Path()
        for ((index, point) in points.withIndex()) {
            if (index == 0) {
                path1.moveTo(point.x * scale, point.y)
            } else {
                path1.lineTo(point.x * scale, point.y)
            }
        }

        points = listOf(
            Point(10f, 210f), Point(50f, 150f), Point(100f, 130f),
            Point(150f, 200f), Point(200f, 80f), Point(250f, 240f),
            Point(300f, 20f), Point(350f, 150f), Point(400f, 50f),
            Point(450f, 240f), Point(500f, 140f)
        )
        val path2 = Path()
        path2.moveTo(points[0].x * scale, points[0].y)
        path2.cubicTo(points[1].x * scale, points[1].y, points[2].x * scale, points[2].y, points[3].x * scale, points[3].y)
        path2.cubicTo(points[4].x * scale, points[4].y, points[5].x * scale, points[5].y, points[6].x * scale, points[6].y)
        path2.cubicTo(points[7].x * scale, points[7].y, points[8].x * scale, points[8].y, points[9].x * scale, points[9].y)
        path2.lineTo(points[10].x * scale, points[10].y)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
                .graphicsLayer()
                .transformable(state)
        ) {

            // 绘制 y 轴
            drawLine(
                start = Offset(10f, 300f),
                end = Offset(10f, 0f),
                color = Color.Black,
                strokeWidth = 2f
            )

            // 绘制 x 轴
            drawLine(
                start = Offset(10f, 300f),
                end = Offset(810f, 300f),
                color = Color.Black,
                strokeWidth = 2f
            )

            drawPath(
                path = path1,
                color = Color.Blue,
                style = Stroke(2f)
            )

            drawPath(
                path = path2,
                color = Color.Green,
                style = Stroke(2f)
            )
        }

    }

    private fun initPath() {


    }

    @Preview
    @Composable
    private fun DefaultPreview() {
        LineChart()
    }
}