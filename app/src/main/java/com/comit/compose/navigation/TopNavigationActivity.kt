package com.comit.compose.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/*
 * Created by Comit on 2021/8/15.
 *
 * references: https://juejin.cn/post/6983968223209193480#heading-3
 */
class TopNavigationActivity : AppCompatActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScrollTabRowView()
        }
    }

    @Composable
    private fun TabRowView() {
        var selectIndex by remember {
            mutableStateOf(0)
        }

        val titles = listOf("Java", "Kotlin", "Android", "Flutter")

        Column {
            TabRow(selectedTabIndex = selectIndex) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectIndex == index,
                        onClick = { selectIndex = index },
                        text = {
                            Text(text = title)
                        }
                    )
                }
            }

            ScreenView(title = titles[selectIndex])
        }
    }

    @ExperimentalPagerApi
    @Composable
    private fun ScrollTabRowView() {
        val titles = listOf("Java", "Kotlin", "Android", "Flutter", "Scala", "Python")
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            pageCount = titles.size, // 总页数
            initialOffscreenLimit = 2, // 预加载的个数
            infiniteLoop = true, // 无限循环
            initialPage = 0 // 初始页面
        )

        Column {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.wrapContentSize(),
                edgePadding = 16.dp
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(text = title)
                        }
                    )
                }
            }
            
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                ScreenView(title = titles[index])
            }
        }
    }
}

@Composable
private fun ScreenView(title: String) {
    Log.d("Compose", "ScreenView, title: $title")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, fontSize = 30.sp)
    }
}