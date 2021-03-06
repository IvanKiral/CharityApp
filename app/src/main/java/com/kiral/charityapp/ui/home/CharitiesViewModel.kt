package com.kiral.charityapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.utils.global_categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository,
    private val charityRepository: CharityRepository
) : ViewModel() {
    val categories = global_categories

    var userId: Int = -1

    private val _charities = mutableStateOf<List<CharityListItem>>(ArrayList())
    val charities: State<List<CharityListItem>>
        get() = _charities

    val error = mutableStateOf<String?>(null)
    val loading = mutableStateOf(false)

    val lst = MutableList(categories.size) { true }
    val selectedCategories = lst.toMutableStateList()

    val showFilter = mutableStateOf(false)

    fun getCharities(id: Int) {
        error.value = null
        charityRepository.search(id, selectedCategories.mapIndexed { index, v -> if(v) index + 1 else -1}.filter { it > -1 }).onEach {
            when (it) {
                is DataState.Success<List<CharityListItem>> -> {
                    loading.value = false
                    _charities.value = it.data
                }
                is DataState.Error -> {
                    loading.value = false
                    error.value = it.error
                }
                is DataState.Loading -> {
                    loading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getId(email: String) {
        profileRepository.login(email).onEach { state ->
            when(state){
                is DataState.Success -> {
                    userId = state.data
                    getCharities(userId)
                }
            }
        }.launchIn(viewModelScope)
    }
}