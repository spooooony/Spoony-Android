package com.spoony.spoony.presentation.explore.component.dialog

import androidx.compose.runtime.Composable
import com.spoony.spoony.core.designsystem.component.dialog.TwoButtonDialog

@Composable
fun ReviewDeleteDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (visible) {
        TwoButtonDialog(
            message = "정말로 리뷰를 삭제할까요?",
            negativeText = "아니요",
            positiveText = "네",
            onClickNegative = onDismiss,
            onClickPositive = {
                onConfirm()
                onDismiss()
            },
            onDismiss = { onDismiss() }
        )
    }
}
