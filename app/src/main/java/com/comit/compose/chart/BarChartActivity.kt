package com.comit.compose.chart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comit.compose.ui.theme.ComposeTheme

/*
 * Created by Comit on 2021/8/14.
 *
 * references: https://juejin.cn/post/6986544238984953886#heading-1
 */
class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTheme {
                BarChart()
            }
        }
    }

    @Preview
    @Composable
    private fun BarChart() {
        val points = listOf(
            Point(10f, 10f), Point(90f, 100f), Point(170f, 30f),
            Point(250f, 200f), Point(330f, 120f), Point(410f, 10f),
            Point(490f, 280f), Point(570f, 100f), Point(650f, 10f),
            Point(730f, 100f), Point(810f, 200f)
        )

        var start by remember { mutableStateOf(false) }
        val heightPre by animateFloatAsState(
            targetValue = if (start) 1f else 0f,
            animationSpec = FloatTweenSpec(duration = 1000)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            val index = identityClickItem(points, it.x, it.y)
                            Toast
                                .makeText(
                                    this@BarChartActivity, "onTap: $index",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    )
                }
        ) {

            // 绘制 y 轴
            drawLine(
                start = Offset(10f, 600f),
                end = Offset(10f, 0f),
                color = Color.Black,
                strokeWidth = 2f
            )

            drawLine(
                start = Offset(10f, 600f),
                end = Offset(850f, 600f),
                color = Color.Black,
                strokeWidth = 2f
            )

            // 绘制 x 轴
            start = true
            for (p in points) {
                val height = (600 - p.y) * heightPre
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(p.x + 20, 600 - height),
                    size = Size(40f, height)
                )
            }

        }
    }

    private fun identityClickItem(points: List<Point>, x: Float, y: Float): Int {
        for ((index, point) in points.withIndex()) {
            if (x > point.x + 20 && x < point.x + 20 + 40) {
                return index
            }
        }

        return -1
    }
}