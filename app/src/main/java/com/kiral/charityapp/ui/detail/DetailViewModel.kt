package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val _charity: MutableState<Charity?> = mutableStateOf(null)
    val charity: State<Charity?>
        get() = _charity

    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val values = DONATION_VALUES
    val selectedValue = mutableStateOf(0)
    val showDialog = mutableStateOf(false)
    val showDonationSuccessDialog = mutableStateOf(false)

    fun getCharity(id: Int, donorId: Int) {
        charityRepository.get(id, donorId).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading.value = true
                }
                is DataState.Success -> {
                    _charity.value = state.data
                    loading.value = false
                }
                is DataState.Error -> {
                    loading.value = false
                    error.value = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun makeDonation(donorId: Int) {
        val value = values.get(selectedValue.value)
        _charity.value?.let { currentCharity ->
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
                        setShowDialog(false)
                        setDonationSuccessDialog(true)
                        _charity.value = _charity.value?.copy(
                            donorDonated = donorDonated + value
                        )
                    }
                    is DataState.Error -> {
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setSelectedValue(value: Int) {
        selectedValue.value = value
    }

    fun setShowDialog(value: Boolean) {
        showDialog.value = value
    }

    fun setDonationSuccessDialog(value: Boolean) {
        showDonationSuccessDialog.value = value
    }
}