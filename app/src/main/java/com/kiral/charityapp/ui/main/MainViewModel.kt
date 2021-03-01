package com.kiral.charityapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    var donor_id: Int? = null

    fun getProfileId(email: String){
        viewModelScope.launch {
            donor_id = profileRepository.login(email)
        }
    }
}