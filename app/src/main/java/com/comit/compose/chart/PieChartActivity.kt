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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comit.compose.ui.theme.ComposeTheme

/*
 * Created by Comit on 2021/8/14.
 *
 * references: https://juejin.cn/post/6986544238984953886#heading-2
 */
class PieChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                PieChart()
            }
        }
    }

    @Preview
    @Composable
    private fun PieChart() {
        val points = listOf(10f, 40f, 20f, 80f, 100f, 60f)
        val colors = listOf(Color.Blue, Color.Yellow, Color.Green,
            Color.Gray, Color.Red, Color.Cyan)
        val sum = points.sum()
        var startAngle = 0f
        val radius = 200f
        val rect = Rect(Offset(-radius, -radius), Size(2 * radius, 2 * radius))
        val path = Path()
        val angles = mutableListOf<Float>()
//        val regions = mutableListOf<Region>()
        var start by remember { mutableStateOf(false) }
        val sweepPre by animateFloatAsState(
            targetValue = if (start) 1f else 0f,
            animationSpec = FloatTweenSpec(duration = 1000)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            val x = it.x - radius
                            val y = it.y - radius
                            var touchAngle = Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble()))
                            //坐标 1,2 象限返回 -180~0  3,4 象限返回 0~180
                            if (x < 0 && y < 0 || x > 0 && y < 0){ // 1,2象限
                                touchAngle += 360
                            }
                            val position = getPositionFromAngle(angles, touchAngle)

                            Toast.makeText(this@PieChartActivity, "onTap: $position",
                                Toast.LENGTH_SHORT).show()
                        }
                    )
                }
        ) {
            translate(radius, radius) {
                start = true
                for ((i , p) in points.withIndex()) {
                    val sweepAngle = p / sum * 360f
                    path.moveTo(0f, 0f)
                    path.arcTo(rect = rect, startAngle, sweepAngle * sweepPre, false)
                    angles.add(sweepAngle)
                    drawPath(path = path, color = colors[i])
                    path.reset()

                    startAngle += sweepAngle
                }
            }
        }
    }

    private fun getPositionFromAngle(angles: List<Float>, touchAngle: Double): Int {
        var totalAngle = 0f
        for ((i, angle) in angles.withIndex()) {
            totalAngle += angle
            if (touchAngle <= totalAngle) {
                return i
            }
        }

        return -1
    }
}