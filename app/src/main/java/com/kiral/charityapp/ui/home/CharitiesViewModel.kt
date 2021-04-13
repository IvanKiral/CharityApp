package com.kiral.charityapp.ui.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.ui.dataStore
import com.kiral.charityapp.utils.Constants
import com.kiral.charityapp.utils.Constants.CATEGORIES_NUMBER
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

    val app: Application
) : AndroidViewModel(app) {


    private val USER_ID = intPreferencesKey("user_id")
    var userId: Int = -1

    var charities by mutableStateOf<List<CharityListItem>>(ArrayList())
        private set

    var charitiesError by mutableStateOf<String?>(null)
        private set
    var charitiesLoading by mutableStateOf(false)
        private set
    var charitiesPagingLoading by mutableStateOf(false)
        private set

    private var categoriesList = MutableList(CATEGORIES_NUMBER) { true }
    var selectedCategories = categoriesList.toMutableStateList()

    var showFilter by mutableStateOf(false)

    var donorRank by mutableStateOf<Int?>(null)
        private set
    var leaderboard by mutableStateOf<List<LeaderBoardProfile>>(ArrayList())
        private set

    var leaderboardError by mutableStateOf<String?>(null)
        private set
    var leaderboardLoading by mutableStateOf(false)
        private set

    var page by mutableStateOf(1)
    var indexPosition = 0

    init {
        val uId: Flow<Int> = app.dataStore.data
            .map { preferences ->
                preferences[USER_ID] ?: -1
            }
        uId.onEach { id ->
            if (id != -1) {
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
            page,
            getSelectedCategories()
        ).onEach { state ->
            charitiesLoading = false
            charitiesPagingLoading = false
            when (state) {
                is DataState.Success<List<CharityListItem>> -> {
                    appendCharities(state.data)
                }
                is DataState.Error -> {
                    charitiesError = state.error
                }
                is DataState.Loading -> {
                    if(page == 1){
                        charitiesLoading = true
                    } else {
                       charitiesPagingLoading = true
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun nextPage() {
        //preventing recomposing so it would call pagination more times
        if ((indexPosition + 1) >= (page * Constants.CHARITIES_PAGE_SIZE)) {
            page += 1
            if (page > 1) {
                getCharities()
            }
        }
    }

    fun getLeaderboard() {
        leaderboardError = null
        charityRepository.getLeaderboard(userId).onEach { state ->
            when (state) {
                is DataState.Success -> {
                    leaderboardLoading = false
                    leaderboard = state.data
                    donorRank = 1
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

    fun onFilterChange() {
        showFilter = !showFilter
        if (!showFilter) {
            reset()
            getCharities()
        }
    }

    private fun getSelectedCategories(): List<Int> {
        // + 1 because categories on database starts by 1
        return selectedCategories
            .mapIndexedNotNull { index, v ->
                if (v) index + 1 else null
            }
    }

    private fun reset() {
        page = 1
        charities = listOf()
    }

    private fun appendCharities(data: List<CharityListItem>){
        val tmp = ArrayList(charities)
        tmp.addAll(data)
        charities = tmp
    }
}