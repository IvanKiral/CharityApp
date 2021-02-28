package com.kiral.charityapp.ui.badges

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BadgesViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _badges: MutableState<List<Badge>> = mutableStateOf(listOf())
    val badges: State<List<Badge>>
        get() = _badges

    fun getBadges(donorId: Int) {
        _badges.value = profileRepository.getBadges(donorId)
    }

}