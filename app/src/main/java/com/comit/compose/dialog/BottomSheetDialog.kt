package com.comit.compose.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/*
 * Created by Comit on 2022/10/19.
 *
 * https://mp.weixin.qq.com/s/TuG7DdNoMbA_xvS9jOgPuA
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomSheetDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    cancelable: Boolean = true,
    canceledOnTouchOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler(enabled = visible) {
        if (cancelable) {
            onDismissRequest()
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 400, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 400, easing = LinearEasing))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x99000000))
                    .clickableNoRipple {
                        if (canceledOnTouchOutside) {
                            onDismissRequest()
                        }
                    }
            )

        }
        InnerDialog(
            visible = visible,
            cancelable = cancelable,
            onDismissRequest = onDismissRequest,
            content = content
        )
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BoxScope.InnerDialog(
    visible: Boolean,
    cancelable: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {

    var offsetY by remember {
        mutableStateOf(0f)
    }
    val offsetYAnimate by animateFloatAsState(targetValue = offsetY)
    var bottomSheetHeight by remember {
        mutableStateOf(0f)
    }
    AnimatedVisibility(
        modifier = Modifier
            .clickableNoRipple {

            }
            .align(alignment = Alignment.BottomCenter)
            .onGloballyPositioned {
                bottomSheetHeight = it.size.height.toFloat()
            }
            .offset(offset = {
                IntOffset(0, offsetYAnimate.roundToInt())
            })
            .draggable(
                state = rememberDraggableState(onDelta = {
                    offsetY = (offsetY + it.toInt()).coerceAtLeast(0f)
                }),
                orientation = Orientation.Vertical,
                onDragStarted = {

                },
                onDragStopped = {
                    if (cancelable && offsetY > bottomSheetHeight / 2) {
                        onDismissRequest()
                    } else {
                        offsetY = 0f
                    }
                }
            ),
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            initialOffsetY = { 2 * it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            targetOffsetY = { it }
        )
    ) {
        DisposableEffect(key1 = null) {
            onDispose {
                offsetY = 0f
            }
        }
        content()
    }

}

/**
 * 点击去掉水波纹效果
 */
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }