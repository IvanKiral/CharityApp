package com.kiral.charityapp.ui.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.ui.dataStore
import com.kiral.charityapp.ui.onboarding.STATE_ONBOARDING_CATEGORIES_KEY
import com.kiral.charityapp.utils.Constants
import com.kiral.charityapp.utils.Constants.CATEGORIES_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_CHARITIES_PAGE_KEY = "charities_state_pages"
const val STATE_CHARITIES_USER_KEY = "charities_state_user"
const val STATE_CHARITIES_FILTER_KEY = "charities_state_filter"
const val STATE_CHARITIES_CATEGORIES_KEY = "charities_state_categories"
const val STATE_CHARITIES_ITEM_POSITION_KEY = "charities_state_item_position"
const val STATE_CHARITIES_ITEM_POSITION_OFFSET_KEY = "charities_state_item_offset_position"

@HiltViewModel
class CharitiesViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository,
    private val state: SavedStateHandle,
    val app: Application
) : AndroidViewModel(app) {

    private var shouldRestoreState = false

    var savedPosition: Pair<Int, Int>? = null
    var scrollPosition = 0
        private set(value) {
            field = value
            state.set(STATE_CHARITIES_ITEM_POSITION_KEY, value)
        }

    var scrollOffset = 0
        private set(value) {
            field = value
            state.set(STATE_CHARITIES_ITEM_POSITION_OFFSET_KEY, value)
        }

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
        private set
    var indexPosition = 0

    init {
        state.get<Int>(STATE_CHARITIES_ITEM_POSITION_KEY)?.let{ position ->
            scrollPosition = position
        }
        state.get<Int>(STATE_CHARITIES_ITEM_POSITION_OFFSET_KEY)?.let{ offset ->
            scrollOffset = offset
        }
        state.get<Boolean>(STATE_CHARITIES_FILTER_KEY)?.let { filter ->
            showFilter = filter
        }
        state.get<List<Boolean>>(STATE_CHARITIES_CATEGORIES_KEY)?.let { categories ->
            selectedCategories = categories.toMutableStateList()
        }
        state.get<Int>(STATE_CHARITIES_PAGE_KEY)?.let { page ->
            shouldRestoreState = true
            setPageValue(page)
        }
        state.get<Int>(STATE_CHARITIES_USER_KEY)?.let { user ->
            userId = user
        }

        val uId: Flow<Int> = app.dataStore.data
            .map { preferences ->
                preferences[USER_ID] ?: -1
            }
        uId.onEach { id ->
            if (id != -1) {
                userId = id
                state.set(STATE_CHARITIES_USER_KEY, userId)
                if (!shouldRestoreState) {
                    getCharities(page)
                } else {
                    restoreState()
                }
                getLeaderboard()
            }
        }.launchIn(viewModelScope)
    }


    //restore state after process death
    private fun restoreState() {
        if (showFilter) {
            return
        }
        viewModelScope.launch {
            charitiesLoading = true
            val results: MutableList<CharityListItem> = mutableListOf()
            repeat(page) { p ->
                charityRepository.search(
                    id = userId,
                    page = p + 1,
                    getSelectedCategories()
                ).collect { state ->
                    when (state) {
                        is DataState.Success -> { results.addAll(state.data) }
                        else -> { }
                    }
                }
                if(p == page - 1){
                    savedPosition = Pair(scrollPosition, scrollOffset)
                    charities = results
                    charitiesLoading = false
                }
            }
        }
        shouldRestoreState = false
    }

    fun getCharities(_page: Int) {
        charitiesError = null
        charityRepository.search(
            userId,
            _page,
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
                    if (_page == 1) {
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
            setPageValue(page + 1)
            if (page > 1) {
                getCharities(page)
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
        state.set(STATE_CHARITIES_FILTER_KEY, showFilter)
        if (!showFilter) {
            reset()
            getCharities(page)
        }
    }

    private fun getSelectedCategories(): List<Int> {
        // + 1 because categories on database starts by 1
        val result = selectedCategories
            .mapIndexedNotNull { index, v ->
                if (v) index + 1 else null
            }
        state.set(STATE_CHARITIES_CATEGORIES_KEY, selectedCategories.toList())
        return result
    }

    fun reset() {
        scrollOffset = 0
        scrollPosition = 0
        charitiesLoading = false
        charitiesError = null
        setPageValue(1)
        charities = listOf()
    }

    private fun appendCharities(data: List<CharityListItem>) {
        val tmp = ArrayList(charities)
        tmp.addAll(data)
        charities = tmp
    }

    fun setPageValue(value: Int) {
        page = value
        state.set(STATE_CHARITIES_PAGE_KEY, page)
    }

    fun setPosition(
        value: Int,
        scrollPosition: Int,
        scrollOffset: Int
    ) {
        indexPosition = value
        this.scrollPosition = scrollPosition
        this.scrollOffset = scrollOffset
    }

    fun setCategories(index: Int){
        val selectedSize = selectedCategories.filter { b -> b }.size
        if (selectedSize > 0) {
            if (selectedSize > 1) {
                selectedCategories[index] = !selectedCategories[index]
            } else if (selectedSize == 1) {
                if (!selectedCategories[index])
                    selectedCategories[index] = !selectedCategories[index]
            }
        }
        state.set(STATE_ONBOARDING_CATEGORIES_KEY, selectedCategories.toList())
    }
}