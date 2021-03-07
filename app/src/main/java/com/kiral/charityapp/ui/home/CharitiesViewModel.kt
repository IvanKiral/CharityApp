package com.kiral.charityapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.utils.Constants.CATEGORIES
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
    val categories = CATEGORIES
    var userId: Int = -1

    private val _charities = mutableStateOf<List<CharityListItem>>(ArrayList())
    val charities: State<List<CharityListItem>>
        get() = _charities

    val charitiesError = mutableStateOf<String?>(null)
    val charitiesLoading = mutableStateOf(false)

    val lst = MutableList(categories.size) { true }
    val selectedCategories = lst.toMutableStateList()

    val showFilter = mutableStateOf(false)

    val leaderboard = mutableStateOf<List<LeaderBoardProfile>>(ArrayList())

    val leaderboardError = mutableStateOf<String?>(null)
    val leaderboardLoading = mutableStateOf(false)



    fun getCharities(id: Int) {
        charitiesError.value = null
        charityRepository.search(id, selectedCategories.mapIndexed { index, v -> if(v) index + 1 else -1}.filter { it > -1 }).onEach {
            when (it) {
                is DataState.Success<List<CharityListItem>> -> {
                    charitiesLoading.value = false
                    _charities.value = it.data
                }
                is DataState.Error -> {
                    charitiesLoading.value = false
                    charitiesError.value = it.error
                }
                is DataState.Loading -> {
                    charitiesLoading.value = true
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
                    getLeaderboard()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getLeaderboard() {
        leaderboardError.value = null
        charityRepository.getLeaderboard(userId).onEach { state ->
            when(state){
                is DataState.Success -> {
                    leaderboardLoading.value = false
                    leaderboard.value = state.data
                }
                is DataState.Loading -> {
                    leaderboardLoading.value = true
                }
                is DataState.Error -> {
                    leaderboardLoading.value = false
                    leaderboardError.value = state.error
                }
            }
        }.launchIn(viewModelScope)
    }
}