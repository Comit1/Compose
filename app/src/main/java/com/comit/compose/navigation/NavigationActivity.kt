package com.comit.compose.navigation

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

/*
 * Created by Comit on 2021/8/15.
 */
class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = initItems()
        setContent {
            MainView(items)
        }
    }

    @Composable
    private fun MainView(items: List<ButtonItem>) {
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

        var item = ButtonItem("Normal") {
            startActivity(Intent(this, NormalNavigationActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Bottom") {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
        items.add(item)

        item = ButtonItem("Top") {
            startActivity(Intent(this, TopNavigationActivity::class.java))
        }
        items.add(item)

        return items
    }
}