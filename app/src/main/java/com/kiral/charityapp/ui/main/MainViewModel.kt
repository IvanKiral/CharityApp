package com.kiral.charityapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    var donor_id: Int? = null

    fun getProfileId(email: String){
        profileRepository.login(email).onEach { state ->
            when(state){
                is DataState.Success -> donor_id = state.data
            }

        }.launchIn(viewModelScope)
    }
}