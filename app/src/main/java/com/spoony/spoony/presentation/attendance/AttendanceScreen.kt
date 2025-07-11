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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowOrientationRules
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.dialog.SpoonDrawDialog
import com.spoony.spoony.core.designsystem.component.image.UrlImage
import com.spoony.spoony.core.designsystem.component.topappbar.TagTopAppBar
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.model.SpoonDrawModel
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray0
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.TagSize
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.core.util.constants.BULLET_POINT
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.attendance.component.SpoonInfoBottomSheet
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
    val lifecycleOwner = LocalLifecycleOwner.current
    val showSnackBar = LocalSnackBarTrigger.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    val showSpoonDraw by viewModel.showSpoonDraw.collectAsStateWithLifecycle()
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

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is AttendanceSideEffect.ShowSnackBar -> {
                        showSnackBar(sideEffect.message)
                    }
                }
            }
    }

    LaunchedEffect(showSpoonDraw) {
        viewModel.getWeeklySpoonDraw()
    }

    AttendanceScreen(
        paddingValues = paddingValues,
        weeklyDate = viewModel.getWeeklyDate(state.weeklyStartDate),
        todayDate = viewModel.today,
        spoonDrawList = (state.spoonDrawList as? UiState.Success<ImmutableList<SpoonDrawModel>>)?.data
            ?: persistentListOf(),
        spoonCount = state.totalSpoonCount,
        onBackButtonClick = navigateUp
    )

    if (showSpoonDraw) {
        SpoonDrawDialog(
            onDismiss = { viewModel.updateShowSpoonDraw(false) },
            onSpoonDrawButtonClick = viewModel::drawSpoon,
            onConfirmButtonClick = { viewModel.updateShowSpoonDraw(false) }
        )
    }
}

@Composable
private fun AttendanceScreen(
    paddingValues: PaddingValues,
    weeklyDate: String,
    todayDate: LocalDate,
    spoonDrawList: ImmutableList<SpoonDrawModel>,
    spoonCount: Int,
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
            .background(SpoonyAndroidTheme.colors.white)
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TagTopAppBar(
            count = spoonCount,
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

        SpoonGrid(
            spoonDrawList = spoonDrawList,
            todayDate = todayDate
        )

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
                imageVector = ImageVector.vectorResource(R.drawable.ic_info_24),
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
    spoonDrawList: ImmutableList<SpoonDrawModel>,
    todayDate: LocalDate
) {
    val density = LocalDensity.current
    val todayDayOfWeek = todayDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

    val balloonYOffsetPx = with(density) { 19.dp.roundToPx() }
    val balloonBuilder = rememberCustomBalloonBuilder()
    var todayBalloon by remember { mutableStateOf<BalloonWindow?>(null) }

    LaunchedEffect(Unit) {
        todayBalloon?.showAlignBottom()
    }

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
            val amount = weeklyList[day]?.spoonAmount ?: 0
            val balloonText = buildString {
                if (day == todayDayOfWeek) append("오늘 ")
                append("${amount}개 획득")
            }

            Balloon(
                key = day,
                builder = balloonBuilder,
                balloonContent = { SpoonBalloon(balloonText) }
            ) { balloon ->
                if (day == todayDayOfWeek) todayBalloon = balloon
                SpoonItem(
                    day = day,
                    spoonImage = weeklyList[day]?.spoonImage,
                    modifier = Modifier.noRippleClickable {
                        if (weeklyList[day] != null) {
                            balloon.showAlignBottom(yOff = -balloonYOffsetPx)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun SpoonBalloon(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = SpoonyAndroidTheme.typography.body1m,
            color = SpoonyAndroidTheme.colors.white
        )
    }
}

@Composable
private fun rememberCustomBalloonBuilder(): Balloon.Builder {
    val color = SpoonyAndroidTheme.colors
    return rememberBalloonBuilder {
        setArrowSize(12)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setArrowOrientationRules(ArrowOrientationRules.ALIGN_FIXED)
        setArrowOrientation(ArrowOrientation.TOP)
        setBalloonAnimation(BalloonAnimation.OVERSHOOT)
        setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
        setBackgroundColor(color.black)
        setPaddingHorizontal(12)
        setPaddingVertical(10)
        setCornerRadius(6f)
        setIsVisibleOverlay(false)
        setDismissWhenTouchOutside(true)
        setDismissWhenClicked(true)
        setAutoDismissDuration(3000)
    }
}

@Composable
private fun SpoonItem(
    day: String,
    spoonImage: String?,
    modifier: Modifier = Modifier
) {
    val (borderColor, backgroundColor, textColor) = if (spoonImage == null) {
        Triple(
            SpoonyAndroidTheme.colors.gray100,
            SpoonyAndroidTheme.colors.gray0,
            SpoonyAndroidTheme.colors.gray300
        )
    } else {
        Triple(
            SpoonyAndroidTheme.colors.main200,
            SpoonyAndroidTheme.colors.main100,
            SpoonyAndroidTheme.colors.main300
        )
    }

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
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

        if (spoonImage == null) {
            Image(
                painter = painterResource(R.drawable.img_plastic_spoon),
                contentDescription = null
            )
        } else {
            UrlImage(
                imageUrl = spoonImage
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
