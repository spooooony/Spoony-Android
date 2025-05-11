package com.spoony.spoony.presentation.userpage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.component.button.FollowButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.theme.black
import com.spoony.spoony.core.designsystem.theme.gray600
import com.spoony.spoony.core.designsystem.theme.gray900
import com.spoony.spoony.core.designsystem.theme.main400
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.core.util.extension.spoonyGradient
import com.spoony.spoony.presentation.userpage.model.UserType
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ProfileIntroSection(
    userType: UserType,
    region: String,
    nickname: String,
    introduction: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFollowing: Boolean = false,
    isBlocked: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f, fill = true)
        ) {
            persistentListOf(
                Triple(region, SpoonyAndroidTheme.typography.body2sb, gray600),
                Triple(nickname, SpoonyAndroidTheme.typography.title3sb, black),
                Triple(introduction, SpoonyAndroidTheme.typography.caption1m, gray900)
            ).forEach { (text, style, color) ->
                Text(
                    text = text,
                    style = style,
                    color = color,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        when (userType) {
            UserType.MY_PAGE -> {
                ProfileEditButton(
                    buttonText = "프로필 수정",
                    onButtonClick = onButtonClick,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            UserType.OTHER_PAGE -> {
                FollowButton(
                    isFollowing = isFollowing,
                    onClick = onButtonClick,
                    modifier = Modifier.padding(start = 8.dp),
                    isSmall = false,
                    isBlocked = isBlocked
                )
            }
        }
    }
}

@Composable
private fun ProfileEditButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = Color.Transparent,
        modifier = modifier
            .spoonyGradient(
                cornerRadius = 20.dp,
                mainColor = main400,
                secondColor = white
            )
            .noRippleClickable(onClick = onButtonClick)
    ) {
        Text(
            text = buttonText,
            style = SpoonyAndroidTheme.typography.body2sb,
            color = white,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileIntroSectionPreview() {
    SpoonyAndroidTheme {
        ProfileIntroSection(
            userType = UserType.MY_PAGE,
            region = "서울 마포구 스푼",
            nickname = "슈퍼 순두부",
            introduction = "자기소개 기본멘트.",
            onButtonClick = {},
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileIntroSectionOtherPagePreview() {
    SpoonyAndroidTheme {
        ProfileIntroSection(
            userType = UserType.OTHER_PAGE,
            region = "서울 마포구 스푼",
            nickname = "슈퍼 순두부",
            introduction = "자기소개 기본멘트.",
            onButtonClick = {},
            isFollowing = false,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
