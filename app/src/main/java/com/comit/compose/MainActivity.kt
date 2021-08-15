package com.comit.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.comit.compose.chart.ChartActivity
import com.comit.compose.lifecycle.LifecycleActivity
import com.comit.compose.navigation.NavigationActivity
import com.comit.compose.ui.theme.ComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = initItems()
        setContent {
            ComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ButtonList(items)
                }
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

        var item = ButtonItem("Chart") {
            startActivity(Intent(this, ChartActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Navigation") {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Lifecycle") {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }
        items.add(item)

        return items
    }
}