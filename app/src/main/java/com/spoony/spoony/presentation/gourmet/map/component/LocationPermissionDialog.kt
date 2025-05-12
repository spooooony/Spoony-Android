package com.spoony.spoony.presentation.gourmet.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun LocationPermissionDialog(
    onDismiss: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 35.dp)
                .background(
                    color = SpoonyAndroidTheme.colors.white,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    top = 15.dp,
                    bottom = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .noRippleClickable(onClick = onDismiss)
                            .align(Alignment.TopEnd)
                    )

                    Text(
                        text = "위치 정보를 사용할 수 없습니다",
                        style = SpoonyAndroidTheme.typography.title1,
                        color = SpoonyAndroidTheme.colors.gray900,
                        modifier = Modifier
                            .padding(top = 30.dp)
                    )
                }

                Text(
                    text = "위치 정보 사용을 원하시면\n설정에서 위치 정보 사용 권한을 허용해 주세요",
                    style = SpoonyAndroidTheme.typography.body2m,
                    color = SpoonyAndroidTheme.colors.gray600,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SpoonyButton(
                        text = "취소",
                        onClick = onNegativeButtonClick,
                        style = ButtonStyle.Tertiary,
                        size = ButtonSize.Xsmall,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    SpoonyButton(
                        text = "설정하기",
                        onClick = onPositiveButtonClick,
                        style = ButtonStyle.Secondary,
                        size = ButtonSize.Xsmall,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
