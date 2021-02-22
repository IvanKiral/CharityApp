package com.kiral.charityapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository,
    private val charityRepository: CharityRepository
): ViewModel(){

    private val _charities = mutableStateOf<List<CharityListItem>>(ArrayList())
    val charities: State<List<CharityListItem>>
        get() = _charities

    fun getCharities(id: Int, region: String) {
        _charities.value =  charityRepository.search(id, region)
    }

    fun getId(email: String): Int{
        val x = profileRepository.login(email)
        return if(x == null) 0 else x.id!!

    }
}