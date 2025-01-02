package com.spoony.spoony.presentation.dummy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spoony.spoony.core.state.UiState
import com.spoony.spoony.domain.entity.DummyEntity

@Composable
fun DummyScreen(
    viewModel: DummyViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val state by viewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    LaunchedEffect(key1 = true) {
        viewModel.getDummyUser(2)
    }

    when (state.user) {
        is UiState.Empty -> {}
        is UiState.Loading -> {}
        is UiState.Failure -> {}
        is UiState.Success -> {
            ShowUser(user = (state.user as UiState.Success<DummyEntity>).data)
        }
    }
}

@Composable
fun ShowUser(
    user: DummyEntity
) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatar)
                .build(),
            contentDescription = ""
        )
        Text(
            text = user.firstName,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
