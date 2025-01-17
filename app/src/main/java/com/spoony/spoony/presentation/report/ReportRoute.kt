package com.spoony.spoony.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.component.textfield.SpoonyLargeTextField
import com.spoony.spoony.core.designsystem.component.topappbar.TitleTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.core.util.extension.addFocusCleaner
import com.spoony.spoony.presentation.report.component.ReportRadioButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReportRoute(
    viewModel: ReportViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    ReportScreen(
        reportOptions = state.reportOptions,
        selectedReportOption = state.selectedReportOption,
        reportContext = state.reportContext,
        onOptionReportSelected = { viewModel.updateSelectedReportOption(it) },
        onContextChanged = { viewModel.updateReportContext(it) }
    )
}

@Composable
private fun ReportScreen(
    reportOptions: ImmutableList<ReportOption>,
    selectedReportOption: ReportOption,
    reportContext: String,
    onOptionReportSelected: (ReportOption) -> Unit,
    onContextChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .addFocusCleaner(focusManager)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TitleTopAppBar(
            title = "신고하기",
            onBackButtonClick = {}
        )

        HorizontalDivider(
            thickness = Dp.Hairline,
            color = SpoonyAndroidTheme.colors.gray100
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SpoonyAndroidTheme.colors.white)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(31.dp))

            Text(
                text = "후기를 신고하는 이유가 무엇인가요?",
                color = SpoonyAndroidTheme.colors.black,
                style = SpoonyAndroidTheme.typography.body1sb
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReportRadioButton(
                selectedOption = selectedReportOption,
                onOptionSelected = onOptionReportSelected,
                options = reportOptions
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "자세한 내용을 적어주세요",
                color = SpoonyAndroidTheme.colors.black,
                style = SpoonyAndroidTheme.typography.body1sb
            )

            Spacer(modifier = Modifier.height(12.dp))

            SpoonyLargeTextField(
                value = reportContext,
                placeholder = "내용을 자세히 적어주시면 신고에 도움이 돼요",
                onValueChanged = onContextChanged,
                maxLength = 300,
                minLength = 1,
                minErrorText = "내용 작성은 필수예요",
                maxErrorText = "글자 수 300자 이하로 입력해 주세요"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .background(
                        color = SpoonyAndroidTheme.colors.gray0,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray300
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "스푸니는 철저한 광고 제한 정책과 모니터링을 실시하고 있어요. 부적절한 후기 작성자를 발견하면, '수저 뺏기'로 그들의 수저를 빼앗아 주세요!",
                    style = SpoonyAndroidTheme.typography.caption1m,
                    color = SpoonyAndroidTheme.colors.gray400
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            SpoonyButton(
                text = "신고하기",
                onClick = {},
                style = ButtonStyle.Secondary,
                size = ButtonSize.Xlarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
private fun ReportScreenPreview() {
    var selectedReportOption by remember { mutableStateOf(ReportOption.ADVERTISEMENT) }

    var reportContext by remember { mutableStateOf("") }

    SpoonyAndroidTheme {
        ReportScreen(
            reportOptions = ReportOption.entries.toImmutableList(),
            selectedReportOption = selectedReportOption,
            reportContext = reportContext,
            onOptionReportSelected = {
                selectedReportOption = it
            },
            onContextChanged = {
                reportContext = it
            }
        )
    }
}
