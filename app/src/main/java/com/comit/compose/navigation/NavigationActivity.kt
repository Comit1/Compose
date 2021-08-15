package com.comit.compose.navigation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-0
 */
class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }

    @Composable
    private fun MainView() {
        val navControl = rememberNavController()
        NavHost(
            navController = navControl,
            startDestination = "first_screen",
            modifier = Modifier.fillMaxHeight()
        ) {
            composable("first_screen") {
                FirstScreen(navControl = navControl)
            }

//            composable("second_screen") {
//                SecondScreen(navControl = navControl)
//            }

            composable(
                route = "second_screen/{userId}/{isShow}",
                // 默认情况下 所有参数都会被解析为字符串，如果不是字符串需要单独指定 type
                arguments = listOf(navArgument("isShow") {
                    type = NavType.BoolType
                })
            ) {
                SecondScreen(
                    navControl = navControl,
                    userId = it.arguments?.getString("userId"),
                    isShow = it.arguments?.getBoolean("isShow") ?: false
                )
            }

//            composable("third_screen") {
//                ThirdScreen(navControl = navControl)
//            }
            composable(
                route = "third_screen?selectable={selectable}",
                arguments = listOf(navArgument("selectable") {
                    defaultValue = "默认值"
                })
            ) {
                ThirdScreen(
                    navControl = navControl,
                    selectable = it.arguments?.getString("selectable")
                )
            }
        }
    }
}

@Composable
fun FirstScreen(navControl: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navControl.navigate("second_screen/12345/true")
            }
        ) {
            Text(text = "I am First, 点击我去 Second")
        }

    }

}

@Composable
fun SecondScreen(navControl: NavController, userId: String?,
    isShow: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navControl.navigate("third_screen?selectable=测试可选参数")
            }
        ) {
            Text(text = "I am Second, 点击我去 Third")
        }
        
        Spacer(modifier = Modifier.size(30.dp))

        Text(text = "userId: $userId")

        if (isShow) {
            Text(text = "测试 Boolean 值")
        }

    }

}

@Composable
fun ThirdScreen(navControl: NavController, selectable: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navControl.navigate("first_screen")
            }
        ) {
            Text(text = "I am Third, 点击我去 First")
        }

        selectable?.run {
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = this)
        }

    }

}