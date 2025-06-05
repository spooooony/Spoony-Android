package com.spoony.spoony.presentation.register.model

import com.spoony.spoony.presentation.register.component.SelectedPhoto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RegisterState(
    val selectedPlace: PlaceState = PlaceState("", "", "", 0.0, 0.0),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val searchResults: ImmutableList<PlaceState> = persistentListOf(),
    val categories: ImmutableList<CategoryState> = persistentListOf(),
    val selectedCategory: CategoryState = CategoryState(0, "", "", ""),
    val menuList: ImmutableList<String> = persistentListOf(""),

    val oneLineReview: String = "",
    val userSatisfactionValue: Float = DEFAULT,
    val detailReview: String = "",
    val optionalReview: String = "",
    val selectedPhotos: ImmutableList<SelectedPhoto> = persistentListOf(),
    val originalPhotoUrls: ImmutableList<String> = persistentListOf(),
    val isPhotoErrorVisible: Boolean = false,

    val currentStep: Float = 1f,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    companion object {
        const val DEFAULT = 50f
    }
}
