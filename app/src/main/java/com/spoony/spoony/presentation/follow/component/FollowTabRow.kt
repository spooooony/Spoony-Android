package com.spoony.spoony.presentation.follow.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray400
import com.spoony.spoony.core.designsystem.theme.main400
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FollowTabRow(
    followerCount: Int,
    followingCount: Int,
    onFollowerTabClick: () -> Unit,
    onFollowingTabClick: () -> Unit,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
) {
    val tabs = remember {
        persistentListOf("팔로워", "팔로잉")
    }

    Box(modifier = modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
            divider = { },
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = main400
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = if (index == 0) onFollowerTabClick else onFollowingTabClick,
                    content = {
                        CustomTabContent(
                            title = title,
                            count = if (index == 0) followerCount else followingCount,
                            isSelected = selectedTabIndex == index,
                            selectedColor = main400,
                            unselectedColor = gray400
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CustomTabContent(
    title: String,
    count: Int,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$title $count",
            style = SpoonyAndroidTheme.typography.body1sb,
            color = if (isSelected) selectedColor else unselectedColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 11.dp)
        )
    }
}

@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit
) {
    TabTransition(selectedContentColor, unselectedContentColor, selected) {
        Column(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = null
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
private fun TabTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(selected)
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = TabFadeInAnimationDuration,
                    delayMillis = TabFadeInAnimationDelay,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = TabFadeOutAnimationDuration,
                    easing = LinearEasing
                )
            }
        }
    ) {
        if (it) activeColor else inactiveColor
    }
    CompositionLocalProvider(
        LocalContentColor provides color,
        content = content
    )
}

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

@Preview(showBackground = true)
@Composable
fun FollowTabRowPreview() {
    SpoonyAndroidTheme {
        Column {
            var selectedTab by remember { mutableStateOf(0) }

            FollowTabRow(
                followerCount = 12,
                followingCount = 24,
                onFollowerTabClick = { selectedTab = 0 },
                onFollowingTabClick = { selectedTab = 1 },
                selectedTabIndex = selectedTab,
                modifier = Modifier
            )
        }
    }
}
