package com.spoony.spoony.presentation.setting.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.gray700
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.constants.BULLET_POINT
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AccountDeleteScreen(
    navigateUp: () -> Unit
) {
    val announcementList = remember {
        persistentListOf(
            "회원 탈퇴 시, 즉시 탈퇴 처리되며 서비스 이용이 불가해요.",
            "탈퇴 시 모든 정보가 사라지며, 회원님의 소중한 정보를 되살릴 수 없어요."
        )
    }

    var isNoticeAgreed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        TitleTopAppBar(
            title = "회원탈퇴",
            onBackButtonClick = navigateUp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            announcementList.forEach { announcement ->
                BulletText(
                    text = announcement,
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = gray700,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isNoticeAgreed,
                    onCheckedChange = {
                        isNoticeAgreed = it
                    },
                    modifier = Modifier
                        .size(26.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "위 유의사항을 확인했습니다.",
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = gray700,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SpoonyButton(
                text = "탈퇴하기",
                size = ButtonSize.Large,
                style = ButtonStyle.Primary,
                enabled = isNoticeAgreed,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BulletText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) = Text(
    text = "$BULLET_POINT $text",
    style = style,
    color = color,
    textAlign = textAlign,
    modifier = modifier
)

@Preview
@Composable
private fun AccountDeleteScreenPreview() {
    SpoonyAndroidTheme {
        AccountDeleteScreen(
            navigateUp = {}
        )
    }
}
