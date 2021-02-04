package com.kiral.charityapp.ui.detail

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val chairtyRepository: CharityRepository
): ViewModel(){

    fun getCharity(id: Int): Charity {
        return chairtyRepository.get(id)
    }
}