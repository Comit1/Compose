package com.comit.compose.chart

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.comit.compose.ButtonItem
import com.comit.compose.ui.theme.ComposeTheme

/*
 * Created by Comit on 2021/8/15.
 */
class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = initItems()
        setContent {
            ComposeTheme {
                ButtonList(items)
            }
        }
    }

    @Composable
    private fun ButtonList(items: List<ButtonItem>) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            for (item in items) {
                Button(
                    onClick = { item.action() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Text(text = item.text)
                }
            }
        }
    }

    private fun initItems(): List<ButtonItem> {
        val items = arrayListOf<ButtonItem>()

        var item = ButtonItem("Line Chart") {
            startActivity(Intent(this, LineChartActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Bar Chart") {
           startActivity(Intent(this, BarChartActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Pie Chart") {
            startActivity(Intent(this, PieChartActivity::class.java))
        }
        items.add(item)


        return items
    }
}