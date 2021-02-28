package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.DonationValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
): ViewModel(){

    private val _project: MutableState<Project?> = mutableStateOf(null)
    val project: State<Project?>
        get() = _project

    val values = DonationValues
    val selectedValue = mutableStateOf(0)
    val showDialog = mutableStateOf(false)
    val showDonationSuccessDialog = mutableStateOf(false)

    // var donorDonated: MutableState<Double>
    //lateinit var actualSum: MutableState<Double>

    fun getProject(id: Int, donorId:Int) {
        viewModelScope.launch {
            _project.value = charityRepository.getProject(id, donorId)
        }
        //donorDonated = mutableStateOf(_project.value!!.donorDonated)
        //actualSum = mutableStateOf(_project.value!!.actualSum)
    }

    fun makeDonation(donorId: Int) {
        viewModelScope.launch {
            _project.value?.let { c ->
                val value = values.get(selectedValue.value)
                if (charityRepository.makeDonationToCharity(c.id, donorId, null, value)) {
                    _project.value = _project.value?.copy()?.apply {
                        donorDonated = donorDonated.plus(value)
                        actualSum = actualSum.plus(value)
                    }
                    setDonationSuccessDialog(true)
                }
                setShowDialog(false)
            }
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