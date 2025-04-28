package com.spoony.spoony.presentation.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable

@Composable
fun FilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftIcon: @Composable (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    isSelected: Boolean = false
) {
    val borderColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray100
    val textColor = if (isSelected) SpoonyAndroidTheme.colors.main400 else SpoonyAndroidTheme.colors.gray600
    val backgroundColor = if (isSelected) SpoonyAndroidTheme.colors.main0 else SpoonyAndroidTheme.colors.gray0
    val horizontalPadding = if (leftIcon != null || rightIcon != null) 14.dp else 12.dp

    Row(
        modifier = modifier
            .noRippleClickable(onClick)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = backgroundColor
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (leftIcon != null) {
            leftIcon()
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text,
            color = textColor,
            style = SpoonyAndroidTheme.typography.body2sb
        )
        if (rightIcon != null) {
            Spacer(modifier = Modifier.width(2.dp))
            rightIcon()
        }
    }
}

// 프리뷰 파라미터를 위한 데이터 클래스
data class FilterChipPreviewData(
    val text: String,
    val isSelected: Boolean,
    val hasLeftIcon: Boolean = false,
    val hasRightIcon: Boolean = false
)

// 프리뷰 파라미터 제공 클래스
class FilterChipPreviewParameterProvider : PreviewParameterProvider<FilterChipPreviewData> {
    override val values: Sequence<FilterChipPreviewData> = sequenceOf(
        FilterChipPreviewData("필터", true, hasLeftIcon = true),
        FilterChipPreviewData("필터", false, hasLeftIcon = true),
        FilterChipPreviewData("로컬 리뷰", true),
        FilterChipPreviewData("로컬 리뷰", false),
        FilterChipPreviewData("카테고리", true, hasRightIcon = true),
        FilterChipPreviewData("카테고리", false, hasRightIcon = true),
        FilterChipPreviewData("지역", true, hasRightIcon = true),
        FilterChipPreviewData("지역", false, hasRightIcon = true),
        FilterChipPreviewData("연령대", true, hasRightIcon = true),
        FilterChipPreviewData("연령대", false, hasRightIcon = true)
    )
}

@Preview
@Composable
private fun FilterChipPreview(
    @PreviewParameter(FilterChipPreviewParameterProvider::class) previewData: FilterChipPreviewData
) {
    SpoonyAndroidTheme {
        FilterChip(
            text = previewData.text,
            isSelected = previewData.isSelected,
            onClick = {},
            leftIcon = if (previewData.hasLeftIcon) {
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_filter_16),
                        modifier = Modifier.size(16.dp),
                        contentDescription = null,
                        tint = if (previewData.isSelected) {
                            SpoonyAndroidTheme.colors.main400
                        } else {
                            SpoonyAndroidTheme.colors.gray400
                        }
                    )
                }
            } else {
                null
            },
            rightIcon = if (previewData.hasRightIcon) {
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_16),
                        modifier = Modifier.size(16.dp),
                        contentDescription = null,
                        tint = if (previewData.isSelected) {
                            SpoonyAndroidTheme.colors.main400
                        } else {
                            SpoonyAndroidTheme.colors.gray400
                        }
                    )
                }
            } else {
                null
            }
        )
    }
}
