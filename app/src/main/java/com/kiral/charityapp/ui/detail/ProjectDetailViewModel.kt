package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.Constants.DONATION_VALUES
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

    var project by mutableStateOf<Project?>(null)
        private set

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    val values = DONATION_VALUES
    var selectedValue by mutableStateOf(0)
    var showDialog by mutableStateOf(false)
    var showDonationSuccessDialog by mutableStateOf(false)

    fun getProject(id: Int, donorId: Int) {
        charityRepository.getProject(id, donorId).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    loading = false
                    project = state.data
                }
                is DataState.Error -> {
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun makeDonation(donorId: Int) {
        val value = values[selectedValue]
        project?.let { currentProject ->
            val donorDonated = currentProject.donorDonated
            val actualSum = currentProject.actualSum
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
                        showDialog = false
                        showDonationSuccessDialog = true
                        project = project?.copy(
                            donorDonated = donorDonated + value,
                            actualSum = actualSum + value
                        )
                    }
                    is DataState.Error -> {
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}