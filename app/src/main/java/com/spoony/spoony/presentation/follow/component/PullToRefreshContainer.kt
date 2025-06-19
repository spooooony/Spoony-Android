package com.spoony.spoony.presentation.follow.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalMaterial3Api
fun PullToRefreshContainer(
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    indicator: @Composable (PullToRefreshState) -> Unit = { pullRefreshState ->
        Indicator(state = pullRefreshState)
    },
    shape: Shape = PullToRefreshDefaults.shape,
    containerColor: Color = PullToRefreshDefaults.containerColor,
    contentColor: Color = PullToRefreshDefaults.contentColor,
    spinnerContainerSize: Dp = 40.dp,
    alpha: () -> Float = { 1f }
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .size(spinnerContainerSize)
                .graphicsLayer {
                    translationY = state.verticalOffset - size.height
                    this.alpha = alpha()
                }
                .background(color = containerColor, shape = shape)
        ) {
            indicator(state)
        }
    }
}
