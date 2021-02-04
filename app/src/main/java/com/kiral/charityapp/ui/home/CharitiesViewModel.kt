package com.kiral.charityapp.ui.home

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
): ViewModel(){
    val charities = charityRepository.search()
}