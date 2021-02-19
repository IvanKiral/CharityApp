package com.kiral.charityapp.ui.home

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
): ViewModel(){
    fun getCharities(email: String, region: String): List<CharityListItem>{
        return charityRepository.search(email, region)
    }
}