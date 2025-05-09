package com.spoony.spoony.presentation.attendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.bottomsheet.SpoonyBasicBottomSheet
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpoonInfoBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val spoonList = remember {
        persistentListOf("일회용 티스푼", "은스푼", "황금 스푼", "다이아스푼")
    }

    val coroutineScope = rememberCoroutineScope()
    val handleDismissRequest: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }

    SpoonyBasicBottomSheet(
        onDismiss = handleDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = { SpoonInfoHeader(onClick = handleDismissRequest) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            spoonList.forEachIndexed { index, spoonName ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    SpoonInfoText(
                        text = spoonName,
                        modifier = Modifier
                            .width(128.dp)
                            .background(SpoonyAndroidTheme.colors.gray100)
                    )

                    SpoonInfoText(
                        text = "${index + 1}개 적립",
                        modifier = Modifier
                            .weight(1f)
                            .background(SpoonyAndroidTheme.colors.gray0)
                    )
                }
            }
        }
    }
}

@Composable
fun SpoonInfoHeader(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .spoonyGradient(cornerRadius = 0.dp)
            .padding(top = 12.dp, bottom = 24.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                contentDescription = null,
                tint = SpoonyAndroidTheme.colors.white,
                modifier = Modifier
                    .noRippleClickable(onClick = onClick)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.img_spoony),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "오늘의 스푼 뽑기",
                    style = SpoonyAndroidTheme.typography.title2,
                    color = SpoonyAndroidTheme.colors.white
                )
                Text(
                    text = "매일 출석하고 오늘의 스푼을 회득하세요.\n스푼으로 비공개 리뷰를 확인할 수 있어요.",
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = SpoonyAndroidTheme.colors.gray400
                )
            }
        }
    }
}

@Composable
private fun SpoonInfoText(
    text: String,
    modifier: Modifier = Modifier
) = Text(
    text = text,
    style = SpoonyAndroidTheme.typography.body1m,
    color = SpoonyAndroidTheme.colors.gray700,
    textAlign = TextAlign.Center,
    modifier = modifier
        .padding(vertical = 12.dp)
)
