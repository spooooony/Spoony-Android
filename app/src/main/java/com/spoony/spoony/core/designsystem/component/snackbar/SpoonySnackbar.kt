package com.spoony.spoony.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spoony.spoony.core.designsystem.event.LocalSnackBarTrigger
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SpoonySnackbar(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(10.dp),
        color = SpoonyAndroidTheme.colors.gray600,
        shadowElevation = 0.dp
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            textAlign = TextAlign.Center,
            style = SpoonyAndroidTheme.typography.body2m,
            color = SpoonyAndroidTheme.colors.white
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SpoonySnackbarPreview() {
    SpoonyAndroidTheme {
        SpoonySnackbar("내 지도에 추가되었어요.")
    }
}

private const val SNACK_BAR_DURATION = 3000L

@Preview
@Composable
private fun SpoonySnackbarUseCasePreview() {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val onShowSnackBar: (String) -> Unit = { text ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val job = launch {
                snackBarHostState.showSnackbar(
                    message = text
                )
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
        }
    }
    SpoonyAndroidTheme {
        CompositionLocalProvider(
            LocalSnackBarTrigger provides onShowSnackBar
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                        SpoonySnackbar(text = snackbarData.visuals.message)
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        onShowSnackBar("내 지도에 추가되었어요.")
                    }) {
                        Text("Show Snackbar")
                    }
                }
            }
        }
    }
}
