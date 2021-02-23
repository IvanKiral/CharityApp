package com.kiral.charityapp.ui.donators

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonorsViewModel
@Inject
constructor(
    val charityRepository: CharityRepository
): ViewModel() {

}