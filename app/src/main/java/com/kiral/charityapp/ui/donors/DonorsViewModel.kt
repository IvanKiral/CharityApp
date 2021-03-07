package com.kiral.charityapp.ui.donors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.Constants.DONORS_PAGE_SIZE
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
    var charityDonors by mutableStateOf<List<Donor>>(listOf())
    var page by mutableStateOf(1)
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var indexPosition = 0

    fun getCharityDonors(charityId: Int, page: Int)  {
        error = null
        charityRepository.getCharityDonors(charityId, page).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    loading = false
                    val tmp = ArrayList(charityDonors)
                    tmp.addAll(state.data)
                    charityDonors = tmp
                }
                is DataState.Error -> {
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun nextPage(charityId: Int){
        //preventing recomposing so it would call pagination more times
        if((indexPosition + 1) >= (page * DONORS_PAGE_SIZE) ){
            page += 1
            if(page > 1){
                getCharityDonors(charityId, page)
            }
        }
    }

    fun retry(charityId: Int){
        page = 1
        getCharityDonors(charityId, page)
    }
}