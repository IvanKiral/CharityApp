package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository
): ViewModel(){

    private val _project: MutableState<Project?> = mutableStateOf(null)
    val project
        get() = _project.value

    lateinit var donorDonated: MutableState<Double>
    lateinit var actualSum: MutableState<Double>

    fun getProject(id: Int, donorEmail:String) {
        _project.value = charityRepository.getProject(id, donorEmail)
        donorDonated = mutableStateOf(_project.value!!.donorDonated)
        actualSum = mutableStateOf(_project.value!!.actualSum)
    }

    fun makeDonation(charityId:Int, donorId: Int, projectId:Int, value: Double): Boolean{
        if(charityRepository.makeDonationToCharity(charityId, donorId, projectId, value)){
            donorDonated.value += value
            actualSum.value += value.toFloat()
            return true
        }
        return false
    }
}