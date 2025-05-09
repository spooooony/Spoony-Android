package com.spoony.spoony.presentation.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.constants.BULLET_POINT
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.attendance.component.SpoonInfoBottomSheet
import com.spoony.spoony.presentation.attendance.model.SpoonDrawModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private val daysList = persistentListOf("월", "화", "수", "목", "금", "토", "일")

@Composable
fun AttendanceRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val systemUiController = rememberSystemUiController()

    DisposableEffect(Unit) {
        systemUiController.setNavigationBarColor(
            color = gray0
        )

        onDispose {
            systemUiController.setNavigationBarColor(
                color = white
            )
        }
    }

    AttendanceScreen(
        paddingValues = paddingValues,
        weeklyDate = state.weeklyStartDate,
        spoonDrawList = (state.spoonDrawList as? UiState.Success<ImmutableList<SpoonDrawModel>>)?.data
            ?: persistentListOf(),
        onBackButtonClick = navigateUp
    )
}

@Composable
private fun AttendanceScreen(
    paddingValues: PaddingValues,
    weeklyDate: String,
    spoonDrawList: ImmutableList<SpoonDrawModel>,
    onBackButtonClick: () -> Unit
) {
    var bottomSheetVisibility by remember { mutableStateOf(false) }

    if (bottomSheetVisibility) {
        SpoonInfoBottomSheet(
            onDismiss = { bottomSheetVisibility = false }
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TagTopAppBar(
            count = 11,
            tagSize = TagSize.Small,
            showBackButton = true,
            onBackButtonClick = onBackButtonClick
        ) {
            Text(
                text = "출석체크",
                style = SpoonyAndroidTheme.typography.title3b,
                color = SpoonyAndroidTheme.colors.black
            )
        }

        TitleSection(
            weeklyDate = weeklyDate,
            onIconClick = { bottomSheetVisibility = true }
        )

        Spacer(modifier = Modifier.weight(1f))

        SpoonGrid(spoonDrawList = spoonDrawList)

        Spacer(modifier = Modifier.weight(1f))

        FooterSection()
    }
}

@Composable
private fun TitleSection(
    weeklyDate: String,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "매일 출석하고\n오늘의 스푼을 획득하세요",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray900
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_error_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.gray400,
                modifier = Modifier
                    .noRippleClickable(onIconClick)
                    .padding(6.dp)
            )
        }

        Text(
            text = weeklyDate,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.gray400
        )
    }
}

@Composable
private fun SpoonGrid(
    spoonDrawList: ImmutableList<SpoonDrawModel>
) {
    val weeklyList: Map<String, SpoonDrawModel> = spoonDrawList.associateBy {
        LocalDate.parse(it.localDate, AttendanceViewModel.hyphenFormatter).dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale.KOREAN
        )
    }

    LazyVerticalGrid(
        columns = GridCells.FixedSize(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        items(daysList) { day ->
            SpoonItem(
                day = day,
                isDrawn = weeklyList[day]?.spoonImage != null,
                spoonImage = weeklyList[day]?.spoonImage
            )
        }
    }
}

@Composable
private fun SpoonItem(
    day: String,
    isDrawn: Boolean,
    spoonImage: String?
) {
    val (borderColor, backgroundColor, textColor) = if (isDrawn) {
        Triple(
            SpoonyAndroidTheme.colors.main200,
            SpoonyAndroidTheme.colors.main100,
            SpoonyAndroidTheme.colors.main300
        )
    } else {
        Triple(
            SpoonyAndroidTheme.colors.gray100,
            SpoonyAndroidTheme.colors.gray0,
            SpoonyAndroidTheme.colors.gray300
        )
    }

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .size(100.dp)
            .border(
                width = 8.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = day,
            style = SpoonyAndroidTheme.typography.title3b,
            color = textColor,
            modifier = Modifier
                .padding(start = 6.dp, top = 2.dp)
        )

        if (isDrawn) {
            Image(
                painter = painterResource(R.drawable.img_wooden_spoon),
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(R.drawable.img_wooden_spoon_grey),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun FooterSection() {
    val announcementList = remember {
        persistentListOf(
            "출석체크는 매일 자정에 리셋 되어요",
            "1일 1회 무료로 참여 가능해요",
            "신규 가입 시 5개의 스푼을 적립해 드려요"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SpoonyAndroidTheme.colors.gray0)
            .padding(horizontal = 20.dp, vertical = 32.dp)
    ) {
        Text(
            text = "유의사항",
            style = SpoonyAndroidTheme.typography.body2sb,
            color = SpoonyAndroidTheme.colors.gray400
        )
        Spacer(Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            announcementList.forEach {
                Text(
                    text = "$BULLET_POINT $it",
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = SpoonyAndroidTheme.colors.gray400
                )
            }
        }
    }
}
