package com.kiral.charityapp.ui.donors

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.DONORS_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    val error = mutableStateOf<String?>(null)
    var indexPosition = 0

    fun getCharityDonors(charityId: Int, page: Int)  {
        charityRepository.getCharityDonors(charityId, page).onEach { state ->
            when (state) {
                is DataState.Loading -> {

                    loading.value = true
                }
                is DataState.Success -> {
                    loading.value = false
                    val tmp = ArrayList(charityDonors.value)
                    tmp.addAll(state.data)
                    charityDonors.value = tmp
                }
                is DataState.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun nextPage(charityId: Int){
        //preventing recomposing so it would call pagination more times
        if((indexPosition + 1) >= (page.value * DONORS_PAGE_SIZE) ){
            page.value = page.value + 1
            if(page.value > 1){
                getCharityDonors(charityId, page.value)
            }
        }
    }
}