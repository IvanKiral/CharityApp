package com.kiral.charityapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.repositories.charities.CharityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val chairtyRepository: CharityRepository
): ViewModel(){

    lateinit var charity: MutableState<Charity>
    lateinit var donorDonated: MutableState<Double>
    lateinit var raised: MutableState<Float>

    fun getCharity(id: Int, donorEmail:String) {
        charity = mutableStateOf( chairtyRepository.get(id, donorEmail ))
        donorDonated = mutableStateOf(charity.value.donorDonated)
        raised = mutableStateOf(charity.value.raised)
    }

    fun makeDonation(charityId:Int, donorId: Int, value: Double): Boolean{
        if(chairtyRepository.makeDonationToCharity(charityId, donorId, null, value)){
            donorDonated.value += value
            raised.value += value.toFloat()
            return true
        }
        return false
    }
}