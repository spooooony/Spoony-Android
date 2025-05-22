package com.spoony.spoony.presentation.auth.termsofservice

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.auth.termsofservice.component.AgreeAllButton
import com.spoony.spoony.presentation.auth.termsofservice.component.AgreeTermsButton
import kotlinx.collections.immutable.persistentListOf

private data class SpoonyTerms(
    val title: String,
    val link: String?,
    val isRequired: Boolean
)

private val termsList = persistentListOf(
    SpoonyTerms(
        title = "만 14세 이상입니다.",
        link = "https://github.com/Hyobeen-Park",
        isRequired = true
    ),
    SpoonyTerms(
        title = "스푸니 서비스 이용약관",
        link = "https://github.com/angryPodo",
        isRequired = true
    ),
    SpoonyTerms(
        title = "개인정보 처리방침",
        link = "https://github.com/Roel4990",
        isRequired = true
    ),
    SpoonyTerms(
        title = "위치기반 서비스 이용약관",
        link = "https://github.com/chattymin",
        isRequired = true
    )
)

@Composable
fun TermsOfServiceRoute(
    paddingValues: PaddingValues,
    navigateToOnboarding: () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(
            color = white
        )
    }

    TermsOfServiceScreen(
        paddingValues = paddingValues,
        navigateToOnboarding = navigateToOnboarding
    )
}

@Composable
private fun TermsOfServiceScreen(
    paddingValues: PaddingValues,
    navigateToOnboarding: () -> Unit
) {
    val context = LocalContext.current
    val isChecked = remember { mutableStateListOf(*Array(termsList.size) { false }) }
    val isRequiredAgreed = termsList
        .filter { it.isRequired }
        .all { term -> isChecked[termsList.indexOf(term)] }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(top = 34.dp, bottom = 20.dp)
            .fillMaxSize()
    ) {
        AgreeAllButton(
            isChecked = isChecked.all { it },
            onClick = {
                val newValue = !isChecked.all { it }
                isChecked.indices.forEach { index ->
                    isChecked[index] = newValue
                }
            }
        )

        Spacer(
            modifier = Modifier
                .height(34.dp)
        )

        termsList.forEachIndexed { index, term ->
            with(term) {
                AgreeTermsButton(
                    title = title,
                    onCheckBoxClick = {
                        isChecked[index] = !isChecked[index]
                    },
                    onTitleClick = {
                        if (link?.isNotBlank() == true) {
                            Intent(Intent.ACTION_VIEW, link.toUri()).apply {
                                context.startActivity(this)
                            }
                        }
                    },
                    isRequired = isRequired,
                    isChecked = isChecked[index],
                    isUnderlined = link?.isNotBlank() == true,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        SpoonyButton(
            text = "동의합니다",
            size = ButtonSize.Xlarge,
            style = ButtonStyle.Primary,
            onClick = navigateToOnboarding,
            enabled = isRequiredAgreed,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
