package com.comit.compose.layout

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density

/*
 * Created by 范伟聪 on 2022/7/31.
 */

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable CustomLayoutScope.() -> Unit
) {

    Layout(
        modifier = modifier,
        content = { CustomLayoutScopeInstance.content() },
        measurePolicy = object : MeasurePolicy {

            private fun IntrinsicMeasurable.matchParentHeight(): Boolean {
                return (parentData as? CustomLayoutParentData)?.matchParentHeight ?: false
            }

            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {

                if (measurables.isEmpty()) {
                    return layout(constraints.minWidth, constraints.minHeight) {}
                }

                val contentConstraints = constraints.copy(minWidth = 0, minHeight = 0)
                val dividerConstraints = constraints.copy(minWidth = 0)
                val placeables = arrayOfNulls<Placeable>(measurables.size)
                val matchParentHeightChildren = mutableListOf<Placeable>()
                var layoutWidth = 0
                var layoutHeight = 0
                measurables.forEachIndexed { index, measurable ->
                    val placeable = if (measurable.matchParentHeight()) {
                        measurable.measure(dividerConstraints).apply {
                            layoutWidth += width
                            matchParentHeightChildren.add(this)
                        }
                    } else {
                        measurable.measure(contentConstraints).apply {
                            layoutWidth += width
                            layoutHeight += height
                        }
                    }
                    placeables[index] = placeable
                }
                return layout(layoutWidth, layoutHeight) {
                    var top = 0
                    var left = 0
                    placeables.forEach { placeable ->
                        if (placeable != null) {
                            if (matchParentHeightChildren.contains(placeable)) {
                                placeable.place(x = left, y = 0)
                            } else {
                                placeable.place(x = left, y = top)
                                top += placeable.height
                            }
                            left += placeable.width
                        }
                    }
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                var maxHeight = 0
                measurables.forEach {
                    if (!it.matchParentHeight()) {
                        maxHeight += it.minIntrinsicHeight(width)
                    }
                }

                return maxHeight
            }
        }
    )

}

@LayoutScopeMarker
@Immutable
interface CustomLayoutScope {

    @Stable
    fun Modifier.matchParentHeight(): Modifier

}

private object CustomLayoutScopeInstance : CustomLayoutScope {

    override fun Modifier.matchParentHeight(): Modifier {
        return this.then(
            LayoutMatchParentHeightImpl(
                matchParentHeight = true,
                inspectorInfo = debugInspectorInfo {
                    name = "matchParentHeight"
                    value = true
                    properties["matchParentHeight"] = true
                }
            )
        )
    }
}

internal data class CustomLayoutParentData(
    val matchParentHeight: Boolean = false
)

internal class LayoutMatchParentHeightImpl(
    val matchParentHeight: Boolean,
    inspectorInfo: InspectorInfo.() -> Unit
) : ParentDataModifier, InspectorValueInfo(inspectorInfo) {

    override fun Density.modifyParentData(parentData: Any?): Any {
        return (parentData as? CustomLayoutParentData)
            ?: CustomLayoutParentData(matchParentHeight = matchParentHeight)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LayoutMatchParentHeightImpl
        if (matchParentHeight != other.matchParentHeight) return false
        return true
    }

    override fun hashCode(): Int {
        return matchParentHeight.hashCode()
    }

    override fun toString(): String {
        return "LayoutMatchParentHeightImpl(matchParentHeight=$matchParentHeight)"
    }

}

