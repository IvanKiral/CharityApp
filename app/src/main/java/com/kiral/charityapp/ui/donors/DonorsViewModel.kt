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
) : ViewModel() {
    var charityDonors by mutableStateOf<List<Donor>>(listOf())
    var page by mutableStateOf(1)
    var showUserDonations by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var indexPosition = 0

    fun getCharityDonors(
        charityId: Int,
        page: Int,
        userId: Int,
        projectId: Int
    ) {
        error = null
        charityRepository.getCharityDonors(
            charityId = charityId,
            page = page,
            projectId = projectId,
            userId = if (showUserDonations) userId else null,
        ).onEach { state ->
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

    fun onProfileIconClicked(charityId: Int, userId: Int, projectId: Int) {
        showUserDonations = !showUserDonations
        reset()
        getCharityDonors(charityId, page, userId, projectId)
    }

    fun nextPage(charityId: Int, userId: Int, projectId: Int) {
        //preventing recomposing so it would call pagination more times
        if ((indexPosition + 1) >= (page * DONORS_PAGE_SIZE)) {
            page += 1
            if (page > 1) {
                getCharityDonors(charityId, page, userId, projectId)
            }
        }
    }

    fun retry(charityId: Int, userId: Int, projectId: Int) {
        reset()
        getCharityDonors(charityId, page, userId, projectId)
    }

    fun reset() {
        page = 1
        charityDonors = listOf()
    }
}