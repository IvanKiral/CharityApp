package com.kiral.charityapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.Constants.CATEGORIES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository,
    dataStore: DataStore<Preferences>
) : ViewModel() {
    val categories = CATEGORIES
    val USER_ID = intPreferencesKey("user_id")
    var userId: Int = -1

    var charities by mutableStateOf<List<CharityListItem>>(ArrayList())
        private set

    var charitiesError by mutableStateOf<String?>(null)
        private set
    var charitiesLoading by mutableStateOf(false)
        private set

    private var categoriesList = MutableList(categories.size) { true }
    var selectedCategories = categoriesList.toMutableStateList()

    var showFilter by mutableStateOf(false)

    var leaderboard by mutableStateOf<List<LeaderBoardProfile>>(ArrayList())
        private set

    var leaderboardError by mutableStateOf<String?>(null)
        private set
    var leaderboardLoading by mutableStateOf(false)
        private set

    init {
        val uId: Flow<Int> = dataStore.data
            .map { preferences ->
                preferences[USER_ID] ?: -1
            }
        uId.onEach { id ->
            if(id != -1){
                userId = id
                getCharities()
                getLeaderboard()
            }
        }.launchIn(viewModelScope)
    }

    fun getCharities() {
        charitiesError = null
        charityRepository.search(
            userId,
            selectedCategories.mapIndexed { index, v -> if(v) index + 1 else -1}.filter { it > -1 }
        ).onEach {
            when (it) {
                is DataState.Success<List<CharityListItem>> -> {
                    charitiesLoading = false
                    charities = it.data
                }
                is DataState.Error -> {
                    charitiesLoading = false
                    charitiesError = it.error
                }
                is DataState.Loading -> {
                    charitiesLoading = true
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLeaderboard() {
        leaderboardError = null
        charityRepository.getLeaderboard(userId).onEach { state ->
            when(state){
                is DataState.Success -> {
                    leaderboardLoading = false
                    leaderboard = state.data
                }
                is DataState.Loading -> {
                    leaderboardLoading = true
                }
                is DataState.Error -> {
                    leaderboardLoading = false
                    leaderboardError = state.error
                }
            }
        }.launchIn(viewModelScope)
    }
}