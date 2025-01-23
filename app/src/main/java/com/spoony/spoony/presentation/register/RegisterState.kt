package com.spoony.spoony.presentation.register

import com.spoony.spoony.presentation.register.component.SelectedPhoto
import com.spoony.spoony.presentation.register.model.Category
import com.spoony.spoony.presentation.register.model.Place
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RegisterState(
    val selectedPlace: Place = Place("", "", "", 0.0, 0.0),
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val searchResults: ImmutableList<Place> = persistentListOf(),
    val categories: ImmutableList<Category> = persistentListOf(),
    val selectedCategory: Category = Category(0, "", "", ""),
    val menuList: ImmutableList<String> = persistentListOf(""),

    val oneLineReview: String = "",
    val detailReview: String = "",
    val selectedPhotos: ImmutableList<SelectedPhoto> = persistentListOf(),
    val isPhotoErrorVisible: Boolean = false,

    val currentStep: Float = 1f,
    val isLoading: Boolean = false,
    val error: String? = null,

    val showRegisterSnackBar: Boolean = true
)
