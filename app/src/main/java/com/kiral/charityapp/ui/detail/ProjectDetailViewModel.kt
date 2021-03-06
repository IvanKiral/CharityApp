package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.DonationValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
) : ViewModel() {

    private val _project: MutableState<Project?> = mutableStateOf(null)
    val project: State<Project?>
        get() = _project

    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val values = DonationValues
    val selectedValue = mutableStateOf(0)
    val showDialog = mutableStateOf(false)
    val showDonationSuccessDialog = mutableStateOf(false)

    fun getProject(id: Int, donorId: Int) {
        charityRepository.getProject(id, donorId).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading.value = true
                }
                is DataState.Success -> {
                    loading.value = false
                    _project.value = state.data
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
        _project.value?.let { currentProject ->
            charityRepository.makeDonationToCharity(
                charityId = currentProject.charityId,
                donorId = donorId,
                projectId = currentProject.id,
                value = value
            ).onEach { state ->
                when (state) {
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        setShowDialog(false)
                        setDonationSuccessDialog(true)
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