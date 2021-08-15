package com.comit.compose.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-1
 */
class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }

    @Composable
    private fun MainView() {
        var count by remember { mutableStateOf(0) }

        Column {
            Button(
                onClick = {
                    count++
                }
            ) {
                Text(text = "Click")
            }

            LaunchedEffect(Unit) {
                Log.d("Compose", "LaunchedEffect, value: $count")
            }

            DisposableEffect(Unit) {
                Log.d("Compose", "DisposableEffect start")
                onDispose {
                    Log.d("Compose", "DisposableEffect onDispose, value: $count")
                }
            }

            SideEffect {
                Log.d("Compose", "SideEffect, value: $count")
            }
            
            Text(text = "You have clicked the button: $count")
        }
    }
}