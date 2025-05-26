package com.spoony.spoony.presentation.placeDetail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun DeleteReviewDialog(
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    TwoButtonDialog(
        message = "정말로 리뷰를 삭제할까요?",
        positiveText = "네",
        onClickPositive = onClickPositive,
        negativeText = "아니요",
        onClickNegative = onClickNegative,
        onDismiss = onClickNegative
    )
}

@Preview
@Composable
private fun DeleteReviewDialogPreview() {
    SpoonyAndroidTheme {
        DeleteReviewDialog(
            onClickPositive = {},
            onClickNegative = {}
        )
    }
}
