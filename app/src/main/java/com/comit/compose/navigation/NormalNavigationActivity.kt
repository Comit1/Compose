package com.comit.compose.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.comit.compose.navigation.viewmodel.NavViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-0
 */
@AndroidEntryPoint
class NormalNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }

    @Composable
    private fun MainView() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "first_screen",
            modifier = Modifier.fillMaxHeight()
        ) {
            composable("first_screen") {
                FirstScreen(navController = navController)
            }

//            composable("second_screen") {
//                SecondScreen(navControl = navController)
//            }

            composable(
                route = "second_screen/{userId}/{isShow}",
                // 默认情况下 所有参数都会被解析为字符串，如果不是字符串需要单独指定 type
                arguments = listOf(navArgument("isShow") {
                    type = NavType.BoolType
                })
            ) {
                SecondScreen(
                    navController = navController,
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
                    navController = navController,
                    selectable = it.arguments?.getString("selectable")
                )
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {

    TestViewModel()

//    TestLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navController.navigate("second_screen/12345/true")
            }
        ) {
            Text(text = "I am First, 点击我去 Second")
        }

    }

}

@Composable
private fun TestLifecycle() {
    val lifecycleOwner = LocalLifecycleOwner.current
    Log.d("TTT", "lifecycleOwner: $lifecycleOwner")
    DisposableEffect(key1 = Unit) {
        val observer = object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                Log.d("TTT", "onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                Log.d("TTT", "onPause")
            }

        }
        Log.d("TTT", "DisposableEffect")
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            Log.d("TTT", "onDispose")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun TestViewModel() {
    val storeOwner = LocalViewModelStoreOwner.current
    Log.d("TTT", "storeOwner: $storeOwner")
//    val navViewModel: NavViewModel = viewModel()
    val navViewModel: NavViewModel = hiltViewModel()
    Log.d("TTT", "viewModel: $navViewModel")
}

@Composable
fun SecondScreen(navController: NavController, userId: String?,
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
                navController.navigate("third_screen?selectable=测试可选参数")
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
fun ThirdScreen(navController: NavController, selectable: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                navController.navigate("first_screen")
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