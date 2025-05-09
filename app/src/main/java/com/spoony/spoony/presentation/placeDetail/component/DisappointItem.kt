package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle

@Composable
fun DisappointItem(
    cons: String,
    onScoopButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBlurred: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = SpoonyAndroidTheme.colors.gray0
            )
    ) {
        Text(
            text = "이거 딱 하나 아쉬워요!",
            style = SpoonyAndroidTheme.typography.body1b,
            color = SpoonyAndroidTheme.colors.black,
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 12.dp,
                    end = 12.dp
                )
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = cons,
                style = SpoonyAndroidTheme.typography.body2m,
                color = SpoonyAndroidTheme.colors.black,
                minLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isBlurred) {
                            Modifier.blur(16.dp)
                        } else {
                            Modifier
                        }
                    )
                    .padding(
                        top = 16.dp,
                        bottom = 20.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
            )
            if (isBlurred) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SpoonyButton(
                        text = "스푼 1개 써서 확인하기",
                        style = ButtonStyle.Primary,
                        size = ButtonSize.Xsmall,
                        onClick = onScoopButtonClick,
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_button_spoon_32),
                                modifier = Modifier.size(32.dp),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    )
                }
            }
        }
    }
}
