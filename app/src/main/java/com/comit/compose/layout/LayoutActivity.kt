package com.comit.compose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.comit.compose.ui.theme.ComposeTheme

/*
 * Created by 范伟聪 on 2022/7/31.
 *
 * https://mp.weixin.qq.com/s/jXf8gQch4_W-bLdApcUnnw
 */
class LayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                CustomLayout()
            }
        }
    }

    @Composable
    private fun CustomLayout() {
        CustomLayout(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .background(color = Color.Yellow)
        ) {

            Spacer(modifier = Modifier
                .background(color = Color.Green)
                .size(size = 40.dp))

            Spacer(modifier = Modifier
                .background(color = Color.Cyan)
                .size(size = 40.dp))


            Divider(
                color = Color.Black,
                modifier = Modifier
                    .width(6.dp)
                    .matchParentHeight()
            )

            Spacer(modifier = Modifier
                .background(color = Color.Magenta)
                .size(size = 40.dp))

            Spacer(modifier = Modifier
                .background(color = Color.Red)
                .size(size = 40.dp))

        }
    }

}