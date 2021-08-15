package com.comit.compose.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-2
 */
class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomMainView()
        }
    }

    @Composable
    private fun BottomMainView() {
        val bottomItems = listOf(Screen.First, Screen.Second, Screen.Third)
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    bottomItems.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(Icons.Filled.Favorite, "") },
                            label = { Text(text = screen.text) },
                            selected = currentRoute == screen.route ,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // 当底部导航导航到在非首页的页面时，执行手机的返回键 回到首页
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    // 从名字就能看出来 跟 activity 的启动模式中的 singleTop 模式一样 避免在栈顶创建多个实例
                                    launchSingleTop = true
                                    // 切换状态的时候保存页面状态
                                    restoreState = true

                                }
                            }
                        )
                    }
                }
            }
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.First.route
            ) {

                composable(Screen.First.route) {
                    FirstScreen()
                }

                composable(Screen.Second.route) {
                    SecondScreen()
                }

                composable(Screen.Third.route) {
                    ThirdScreen()
                }

            }
        }
    }
}

@Composable
private fun FirstScreen() {
    Log.d("Compose", "FirstScreen")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "First", fontSize = 30.sp)
    }
}

@Composable
private fun SecondScreen() {
    Log.d("Compose", "SecondScreen")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Second", fontSize = 30.sp)
    }
}

@Composable
private fun ThirdScreen() {
    Log.d("Compose", "ThirdScreen")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Third", fontSize = 30.sp)
    }
}

enum class Screen(val text: String, val route: String) {
    First("First", "first_screen"),
    Second("Second", "second_screen"),
    Third("Third", "third_screen")
}