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
import kotlinx.coroutines.launch
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

    val lst = MutableList(categories.size) { false }
    val selected = lst.toMutableStateList()

    val showFilter = mutableStateOf(false)

    init{

    }

    fun getCharities(id: Int, region: String) {
        charityRepository.search(id, region).onEach {
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
        viewModelScope.launch {
            userId = profileRepository.login(email)!!
            getCharities(userId, "svk")
        }
    }
}