package com.kiral.charityapp.ui.main

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.kiral.charityapp.repositories.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository,
    val app: Application,
) : AndroidViewModel(app) {

    var navigateToCharitiesFragment by mutableStateOf(false)
    var navigateToWelcomeFragment by mutableStateOf(false)

    var error by mutableStateOf<String?>(null)
    var loading by mutableStateOf(false)
        private set
    var userId: Int? = null

//    fun getProfileId(email: String) {
//        error = null
//        profileRepository.login(email).onEach { state ->
//            when (state) {
//                is DataState.Success -> {
//                    userId = state.data
//                    loading = false
//                    error = null
//                    navigateToCharitiesFragment = true
//                }
//                is DataState.Loading -> {
//                    loading = true
//                }
//                is DataState.Error -> {
//                    error = state.error
//                }
//            }
//
//        }.launchIn(viewModelScope)
//    }
}