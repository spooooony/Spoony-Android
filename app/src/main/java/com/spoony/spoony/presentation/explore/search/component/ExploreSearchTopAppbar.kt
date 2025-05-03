package com.spoony.spoony.presentation.explore.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.component.textfield.SpoonySearchTextField
import com.spoony.spoony.core.designsystem.component.topappbar.SpoonyBasicTopAppBar
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.core.util.extension.noRippleClickable
import com.spoony.spoony.presentation.explore.search.type.SearchType

@Composable
fun ExploreSearchTopAppbar(
    value: String,
    onValueChanged: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onSearchAction: () -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    searchType: SearchType = SearchType.USER
) {
    val placeholder = if (searchType == SearchType.USER) "유저 닉네임으로 검색" else "리뷰 키워드으로 검색"
    SpoonyBasicTopAppBar(
        modifier = modifier
            .background(SpoonyAndroidTheme.colors.white),
        navigationIcon = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(32.dp)
                    .noRippleClickable(onBackButtonClick)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_24),
                    contentDescription = null,
                    tint = SpoonyAndroidTheme.colors.gray700
                )
            }
        }
    ) {
        SpoonySearchTextField(
            value = value,
            onValueChanged = onValueChanged,
            placeholder = placeholder,
            modifier = Modifier
                .padding(start = 12.dp, end = 20.dp),
            focusRequester = focusRequester,
            onSearchAction = onSearchAction
        )
    }
}