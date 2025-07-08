package com.spoony.spoony.presentation.gourmet.map.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.overlay.OverlayImage
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.gourmet.map.model.PlaceReviewModel

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SpoonyMapMarker(
    review: PlaceReviewModel,
    captionText: String,
    selectedMarkerId: Int,
    onClick: () -> Unit
) {
    Marker(
        state = MarkerState(LatLng(review.latitude, review.longitude)),
        captionText = captionText,
        captionColor = SpoonyAndroidTheme.colors.black,
        captionHaloColor = SpoonyAndroidTheme.colors.white,
        captionRequestedWidth = 10.dp,
        captionOffset = (-15).dp,
        iconTintColor = Color.Unspecified,
        icon = OverlayImage.fromResource(
            if (selectedMarkerId == review.placeId) {
                R.drawable.ic_selected_marker
            } else {
                R.drawable.ic_unselected_marker
            }
        ),
        onClick = {
            onClick()
            true
        }
    )
}
