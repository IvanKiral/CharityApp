package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.DonationValues
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val chairtyRepository: CharityRepository
) : ViewModel() {
    private val _charity: MutableState<Charity?> = mutableStateOf(null)
    val charity: State<Charity?>
        get() = _charity

    val values = DonationValues
    val selectedValue = mutableStateOf(0)
    val showDialog = mutableStateOf(false)
    val showDonationSuccessDialog = mutableStateOf(false)

    fun getCharity(id: Int, donorId: Int) {
        _charity.value = chairtyRepository.get(id, donorId)
    }

    fun makeDonation(): Boolean {
        _charity.value?.let { c ->
            val value = values.get(selectedValue.value)
            if (chairtyRepository.makeDonationToCharity(c.id, c.donorId, null, value)) {
                _charity.value = _charity.value?.copy()?.apply {
                    donorDonated = donorDonated.plus(value)
                    raised = raised.plus(value).toFloat()
                }
                return true
            }
        }
        return false
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