package com.kiral.charityapp.ui.detail

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

    fun getProject(id: Int, donorEmail:String): Project {
        return charityRepository.getProject(id, donorEmail)
    }
}