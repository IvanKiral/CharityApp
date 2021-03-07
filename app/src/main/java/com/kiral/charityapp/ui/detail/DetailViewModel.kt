package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.Constants.DONATION_VALUES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
) : ViewModel() {
    var charity by mutableStateOf<Charity?>(null)
        private set

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    val values = DONATION_VALUES
    var selectedValue by mutableStateOf(0)
    var showDialog by mutableStateOf(false)
    var showDonationSuccessDialog by mutableStateOf(false)

    fun getCharity(id: Int, donorId: Int) {
        charityRepository.get(id, donorId).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    charity = state.data
                    loading = false
                }
                is DataState.Error -> {
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun makeDonation(donorId: Int) {
        val value = values.get(selectedValue)
        charity?.let { currentCharity ->
            val donorDonated = currentCharity.donorDonated
            charityRepository.makeDonationToCharity(
                charityId = currentCharity.id,
                donorId = donorId,
                projectId = null,
                value = value
            ).onEach { state ->
                when (state) {
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        showDialog = false
                        showDonationSuccessDialog = true
                        charity = charity?.copy(
                            donorDonated = donorDonated + value
                        )
                    }
                    is DataState.Error -> {
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}