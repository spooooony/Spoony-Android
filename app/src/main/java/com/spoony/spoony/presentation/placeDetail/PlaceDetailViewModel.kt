package com.spoony.spoony.presentation.placeDetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PlaceDetailViewModel @Inject constructor() : ViewModel() {
    var state: MutableStateFlow<PlaceDetailState> =
        MutableStateFlow(PlaceDetailState())
        private set
}
