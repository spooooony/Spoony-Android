package com.spoony.spoony.presentation.auth.termsofservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.spoony.spoony.core.designsystem.component.button.SpoonyButton
import com.spoony.spoony.core.designsystem.theme.white
import com.spoony.spoony.core.designsystem.type.ButtonSize
import com.spoony.spoony.core.designsystem.type.ButtonStyle
import com.spoony.spoony.presentation.auth.termsofservice.component.AgreeAllButton
import com.spoony.spoony.presentation.auth.termsofservice.component.AgreeTermsButton
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TermsOfServiceRoute(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(
            color = white
        )
    }

    TermsOfServiceScreen(
        paddingValues = paddingValues,
        navigateToMap = navigateToMap
    )
}

@Composable
private fun TermsOfServiceScreen(
    paddingValues: PaddingValues,
    navigateToMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(top = 34.dp, bottom = 20.dp)
            .fillMaxSize()
    ) {
        AgreeAllButton(
            onClick = {}
        )

        Spacer(
            modifier = Modifier
                .height(34.dp)
        )

        persistentListOf(
            Pair("만 14세 이상입니다.", false),
            Pair("스푸니 서비스 이용약관", true),
            Pair("개인정보 처리 방침", true),
            Pair("위치기반 서비스 이용약관", true)
        ).forEach { (title, isUnderlined) ->
            AgreeTermsButton(
                title = title,
                onCheckBoxClick = {},
                isRequired = true,
                isUnderlined = isUnderlined,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        SpoonyButton(
            text = "동의합니다",
            size = ButtonSize.Xlarge,
            style = ButtonStyle.Primary,
            onClick = navigateToMap,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
