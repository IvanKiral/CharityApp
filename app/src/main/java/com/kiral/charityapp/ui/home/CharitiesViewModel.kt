package com.kiral.charityapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.utils.global_categories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository,
    private val charityRepository: CharityRepository
): ViewModel(){
    val categories = global_categories

    private val _charities = mutableStateOf<List<CharityListItem>>(ArrayList())
    val charities: State<List<CharityListItem>>
        get() = _charities

    val lst = MutableList(categories.size) { false }
    val selected = lst.toMutableStateList()

    val showFilter = mutableStateOf(false)

    fun getCharities(id: Int, region: String) {
        _charities.value =  charityRepository.search(id, region)
    }

    fun getId(email: String): Int{
        val x = profileRepository.login(email)
        return if(x == null) 0 else x.id!!
    }
}