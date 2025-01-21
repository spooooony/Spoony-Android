package com.spoony.spoony.presentation.report.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.spoony.spoony.core.designsystem.component.dialog.SingleButtonDialog
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme

@Composable
fun ReportCompleteDialog(
    onClick: () -> Unit
) {
    SingleButtonDialog(
        message = "신고가 접수되었어요",
        text = "확인",
        onClick = onClick,
        onDismiss = {}
    )
}

@Preview
@Composable
private fun ScoopDialogPreview() {
    SpoonyAndroidTheme {
        ReportCompleteDialog(
            onClick = {}
        )
    }
}
