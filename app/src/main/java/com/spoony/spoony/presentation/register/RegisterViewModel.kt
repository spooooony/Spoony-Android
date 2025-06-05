package com.spoony.spoony.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spoony.spoony.core.state.ErrorType
import com.spoony.spoony.core.util.extension.onLogFailure
import com.spoony.spoony.domain.repository.RegisterRepository
import com.spoony.spoony.domain.repository.TooltipPreferencesRepository
import com.spoony.spoony.presentation.register.component.SelectedPhoto
import com.spoony.spoony.presentation.register.model.CategoryState
import com.spoony.spoony.presentation.register.model.PlaceState
import com.spoony.spoony.presentation.register.model.RegisterState
import com.spoony.spoony.presentation.register.model.RegisterType
import com.spoony.spoony.presentation.register.model.toModel
import com.spoony.spoony.presentation.register.model.toRegisterPostEntity
import com.spoony.spoony.presentation.register.model.toRegisterState
import com.spoony.spoony.presentation.register.model.toUpdatePostEntity
import com.spoony.spoony.presentation.register.navigation.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RegisterRepository,
    private val tooltipPreferencesRepository: TooltipPreferencesRepository
) : ViewModel() {

    val tooltipShownFlow: Flow<Boolean> = tooltipPreferencesRepository.isTooltipShown()

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect: SharedFlow<RegisterSideEffect>
        get() = _sideEffect.asSharedFlow()

    private val registerInfo = savedStateHandle.toRoute<Register>()
    val registerType = registerInfo.registerType
    val postId = registerInfo.postId

    init {
        loadCategories()
    }

    fun loadState() {
        if (registerType != RegisterType.EDIT || postId == null) {
            return
        }

        viewModelScope.launch {
            repository.getPostDetail(postId)
                .onSuccess { postDetail ->
                    _state.update {
                        postDetail.toModel().toRegisterState(it)
                    }
                }
                .onLogFailure {
                    _sideEffect.emit(RegisterSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getFoodCategories()
                .onSuccess { categoryEntities ->
                    _state.update { state ->
                        state.copy(
                            categories = categoryEntities.map { it.toModel() }.toImmutableList()
                        )
                    }
                }
        }
    }

    fun clearSearchResults() {
        _state.update { it.copy(searchResults = persistentListOf()) }
    }

    fun searchPlace(query: String) {
        viewModelScope.launch {
            repository.searchPlace(query)
                .onSuccess { placeEntities ->
                    if (placeEntities.isEmpty()) {
                        _sideEffect.emit(RegisterSideEffect.ShowSnackbar("검색 결과가 없어요"))
                    }
                    _state.update {
                        it.copy(
                            searchResults = placeEntities.map { entity ->
                                entity.toModel()
                            }.toImmutableList()
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun selectPlace(place: PlaceState) {
        viewModelScope.launch {
            repository.checkDuplicatePlace(
                latitude = place.latitude,
                longitude = place.longitude
            ).onSuccess { isDuplicate ->
                if (isDuplicate) {
                    _state.update { it.copy(searchResults = persistentListOf()) }
                    _sideEffect.emit(RegisterSideEffect.ShowSnackbar("앗! 이미 등록한 맛집이에요"))
                } else {
                    _state.update {
                        it.copy(
                            selectedPlace = place,
                            searchQuery = "",
                            searchResults = persistentListOf()
                        )
                    }
                }
            }.onLogFailure {
                _sideEffect.emit(RegisterSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
            }
        }
    }

    fun clearSelectedPlace() {
        _state.update {
            it.copy(
                selectedPlace = PlaceState.empty(),
                searchQuery = ""
            )
        }
    }

    fun selectCategory(category: CategoryState) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun updateMenu(index: Int, value: String) {
        _state.update {
            if (index >= it.menuList.size) return@update it

            it.copy(
                menuList = buildList {
                    addAll(it.menuList)
                    set(index, value)
                }.toImmutableList()
            )
        }
    }

    fun addMenu() {
        _state.update {
            if (it.menuList.size >= MAX_MENU_COUNT) {
                return@update it
            }
            it.copy(
                menuList = (it.menuList + "").toImmutableList()
            )
        }
    }

    fun removeMenu(index: Int) {
        _state.update {
            if (it.menuList.size <= MIN_MENU_COUNT) {
                it.copy(
                    menuList = persistentListOf("")
                )
            } else {
                it.copy(
                    menuList = buildList {
                        addAll(it.menuList.take(index))
                        addAll(it.menuList.drop(index + 1))
                    }.toImmutableList()
                )
            }
        }
    }

    fun updateUserSatisfactionValue(value: Float) {
        _state.update { it.copy(userSatisfactionValue = value) }
    }

    fun updateStep(step: RegisterSteps) {
        _state.update { it.copy(currentStep = step.step) }
    }

    fun updateOptionalReview(review: String) {
        _state.update {
            it.copy(optionalReview = review)
        }
    }

    fun updateDetailReview(review: String) {
        _state.update {
            it.copy(detailReview = review)
        }
    }

    fun updatePhotos(photos: List<SelectedPhoto>) {
        _state.update {
            it.copy(
                selectedPhotos = photos.take(MAX_PHOTO_COUNT).toImmutableList(),
                isPhotoErrorVisible = photos.size < MIN_PHOTO_COUNT
            )
        }
    }

    fun checkFirstStepValidation(): Boolean = with(_state.value) {
        selectedPlace.placeName.isNotEmpty() &&
            selectedCategory.categoryId != 0 &&
            menuList.any { it.isNotBlank() }
    }

    fun checkSecondStepValidation(): Boolean = with(_state.value) {
        detailReview.trim().length >= MIN_DETAIL_REVIEW_LENGTH &&
            selectedPhotos.size >= MIN_PHOTO_COUNT
    }

    private fun calculateDeleteImageUrls(): List<String> {
        return with(_state.value) {
            val serverUris = selectedPhotos
                .filter { it.isFromServer }
                .map { it.uri.toString() }

            originalPhotoUrls.filter { it !in serverUris }
        }
    }

    fun registerPost(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (registerType) {
                RegisterType.CREATE -> {
                    repository.registerPost(_state.value.toRegisterPostEntity())
                        .onSuccess {
                            _state.update { it.copy(isLoading = false) }
                            resetState()
                            onSuccess()
                        }.onLogFailure {
                            _state.update { it.copy(isLoading = false) }
                            _sideEffect.emit(RegisterSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                        }
                }
                RegisterType.EDIT -> {
                    if (postId != null) {
                        repository.updatePost(_state.value.toUpdatePostEntity(postId, calculateDeleteImageUrls()))
                            .onSuccess {
                                _state.update { it.copy(isLoading = false) }
                                resetState()
                                onSuccess()
                            }.onLogFailure {
                                _state.update { it.copy(isLoading = false) }
                                _sideEffect.emit(RegisterSideEffect.ShowError(ErrorType.UNEXPECTED_ERROR))
                            }
                    }
                }
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _state.update {
                RegisterState(
                    categories = it.categories,
                    showRegisterSnackBar = false
                )
            }
        }
    }

    fun hideRegisterSnackBar() {
        viewModelScope.launch {
            tooltipPreferencesRepository.disableTooltip()
        }
    }

    companion object {
        const val MIN_MENU_COUNT = 1
        const val MAX_MENU_COUNT = 3
        const val MIN_PHOTO_COUNT = 1
        const val MAX_PHOTO_COUNT = 5
        const val MIN_DETAIL_REVIEW_LENGTH = 1
        const val MAX_DETAIL_REVIEW_LENGTH = 500
        const val MAX_OPTIONAL_REVIEW_LENGTH = 100
    }
}

sealed class RegisterSideEffect {
    data class ShowSnackbar(val message: String) : RegisterSideEffect()
    data class ShowError(val errorType: ErrorType) : RegisterSideEffect()
}

enum class RegisterSteps(val step: Float) {
    INIT(1f),
    FINAL(2f)
}
