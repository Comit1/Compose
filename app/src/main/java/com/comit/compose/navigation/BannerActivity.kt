package com.comit.compose.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-4
 */
class BannerActivity : AppCompatActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = listOf(
            "https://wanandroid.com/blogimgs/8a0131ac-05b7-4b6c-a8d0-f438678834ba.png",
            "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
            "https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png"
        )

        setContent {
            MainView(items = items)
        }
    }

    @ExperimentalPagerApi
    @Composable
    private fun MainView(items: List<String>) {
        val pagerState = rememberPagerState(
            pageCount = items.size, // 总页数
            initialOffscreenLimit = 1, // 预加载的个数
            infiniteLoop = true, // 无限循环
            initialPage = 0 // 初始页面
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color.Yellow)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { index ->
                Log.d("Compose", "index: $index")
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberCoilPainter(request = items[index]),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }
        }
    }
}