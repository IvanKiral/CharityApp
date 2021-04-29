package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

const val STATE_CHARITY_KEY = "detail_state_charity_id"
const val STATE_CHARITY_DONOR_KEY = "detail_state_user_id"
const val STATE_CHARITY_DONATION_KEY = "detail_state_donation_field"

@HiltViewModel
class CharityDetailViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository,
    private val state: SavedStateHandle,
) : ViewModel() {
    var charity by mutableStateOf<Charity?>(null)
        private set

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set
    var donationLoading by mutableStateOf(false)
        private set
    var donationError by mutableStateOf<String?>(null)

    var showDonate by mutableStateOf(false)
    var showDonationSuccessDialog by mutableStateOf(false)

    init{
        restoreState()
    }

    private fun restoreState() {
        state.get<Int>(STATE_CHARITY_KEY)?.let { charityId ->
            state.get<Int>(STATE_CHARITY_DONOR_KEY)?.let { donorId ->
                getCharity(charityId, donorId)
            }
        }
        state.get<Boolean>(STATE_CHARITY_DONATION_KEY)?.let { shown ->
            showDonate = shown
        }
    }

    fun getCharity(id: Int, userid: Int) {
        state.set(STATE_CHARITY_KEY, id)
        state.set(STATE_CHARITY_DONOR_KEY, userid)
        error = null
        charityRepository.get(id, userid).onEach { state ->
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

    fun makeDonation(donorId: Int, value: Double) {
        charity?.let { currentCharity ->
            val donorDonated = currentCharity.donorDonated
            val raised = currentCharity.raised
            val peopleDonated = currentCharity.peopleDonated
            charityRepository.makeDonationToCharity(
                charityId = currentCharity.id,
                donorId = donorId,
                projectId = null,
                value = value
            ).onEach { state ->
                when (state) {
                    is DataState.Loading -> {
                        donationLoading = true
                    }
                    is DataState.Success -> {
                        donationLoading = false
                        showDonationSuccessDialog = true
                        charity = charity?.copy(
                            donorDonated = donorDonated + value,
                            raised = raised + value,
                            peopleDonated = peopleDonated + 1
                        )
                    }
                    is DataState.Error -> {
                        donationLoading = false
                        donationError = state.error
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onExtraDonateButtonPressed(){
        showDonate = !showDonate
        state.set(STATE_CHARITY_DONATION_KEY, showDonate)
    }

    fun onDonateButtonPressed(donorId: Int, value: String){
        makeDonation(donorId, value.toDouble())
    }

    fun setDonationSuccessDialog(value: Boolean){
        showDonationSuccessDialog = value
    }

    fun shouldShowDonationFailedDialog(): Boolean = donationError != null
}