package com.comit.compose.dialog

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * Created by Comit on 2022/10/19.
 */
class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()

        }
    }

    @Composable
    private fun MainView() {
        Box(modifier = Modifier.fillMaxSize()) {
            var visible by remember { mutableStateOf(false) }
            Button(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                onClick = {
                    visible = true
                }
            ) {
                Text(text = "BottomSheetDialog")
            }

            BottomSheetDialog(
                modifier = Modifier.fillMaxSize(),
                visible = visible,
                cancelable = true,
                canceledOnTouchOutside = true,
                onDismissRequest = {
                    visible = false
                }
            ) {
                DialogContent {
                    visible = false
                }
            }

        }
    }

    @Composable
    private fun DialogContent(onDismissRequest: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.7f)
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = Color(0xFF009688)),
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    modifier = Modifier.padding(all = 4.dp),
                    text = "dimissDialog",
                    fontSize = 16.sp
                )
            }

        }
    }
}