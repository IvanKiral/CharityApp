package com.kiral.charityapp.ui.donors

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.DONORS_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonorsViewModel
@Inject
constructor(
    val charityRepository: CharityRepository
): ViewModel() {
    val charityDonors = mutableStateOf<List<Donor>>(listOf())
    val page = mutableStateOf(1)
    val loading = mutableStateOf(false)
    var indexPosition = 0

    fun getCharityDonors(charityId: Int, page: Int)  {
        val tmp = ArrayList(charityDonors.value)
        viewModelScope.launch {
            tmp.addAll(charityRepository.getCharityDonors(charityId, page))
            charityDonors.value = tmp.toList()
        }
    }

    fun nextPage(charityId: Int){
        //preventing recomposing so it would call pagination more times
        if((indexPosition + 1) >= (page.value * DONORS_PAGE_SIZE) ){
            loading.value = true
            page.value = page.value + 1
            if(page.value > 1){
                getCharityDonors(charityId, page.value)
            }
            loading.value = false
        }
    }
}