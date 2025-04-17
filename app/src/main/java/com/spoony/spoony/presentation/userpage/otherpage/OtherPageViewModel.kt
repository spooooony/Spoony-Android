package com.spoony.spoony.presentation.userpage.otherpage

import androidx.lifecycle.ViewModel
import com.spoony.spoony.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtherPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}
