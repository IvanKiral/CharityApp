package com.kiral.charityapp.ui.donors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.utils.Constants.DONORS_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_DONORS_PAGE_KEY = "donors_state_pages"
const val STATE_DONORS_USER_KEY = "donors_state_user"
const val STATE_DONORS_CHARITY_KEY = "donors_state_charity"
const val STATE_DONORS_PROJECT_KEY = "donors_state_project"
const val STATE_DONORS_FILTER_KEY = "donors_state_filter"
const val STATE_DONORS_POSITION_KEY = "donors_state_position"
const val STATE_DONORS_ITEM_POSITION_KEY = "donors_state_item_position"
const val STATE_DONORS_ITEM_POSITION_OFFSET_KEY = "donors_state_item_position_offset"

@HiltViewModel
class DonorsViewModel
@Inject
constructor(
    private val charityRepository: CharityRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    var savedPosition: Pair<Int, Int>? = null
    var charityDonors by mutableStateOf<List<Donor>>(listOf())
    var page by mutableStateOf(1)
    var showUserDonations by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var itemPosition = 0

    var scrollPosition = 0
        private set(value) {
            field = value
            state.set(STATE_DONORS_ITEM_POSITION_KEY, value)
        }

    var scrollOffset = 0
        private set(value) {
            field = value
            state.set(STATE_DONORS_ITEM_POSITION_OFFSET_KEY, value)
        }

    var userId: Int? = null
    var projectId: Int? = null
    var charityId: Int? = null

    init {
        // collect states from safe arg
        userId = state.get("userId")
        projectId = state.get("projectId")
        charityId = state.get("charityId")

        state.get<Int>(STATE_DONORS_ITEM_POSITION_KEY)?.let { position ->
            scrollPosition = position
        }

        state.get<Int>(STATE_DONORS_ITEM_POSITION_OFFSET_KEY)?.let { offset ->
            scrollOffset = offset
        }
        state.get<Int>(STATE_DONORS_POSITION_KEY)?.let { position ->
            itemPosition = position
        }
        state.get<Int>(STATE_DONORS_PAGE_KEY)?.let { p ->
            setPageValue(p)
        }
        state.get<Boolean>(STATE_DONORS_FILTER_KEY)?.let { show ->
            showUserDonations = show
        }
        state.get<Int>(STATE_DONORS_USER_KEY)?.let { user ->
            userId = user
        }
        state.get<Int>(STATE_DONORS_CHARITY_KEY)?.let { charity ->
            charityId = charity
        }
        state.get<Int>(STATE_DONORS_PROJECT_KEY)?.let { project ->
            projectId = project
        }

        if(scrollPosition != 0 || scrollOffset != 0){
            restoreState()
        } else{
            getCharityDonors(charityId!!, page, userId!!, projectId!!)
        }
    }

    fun getCharityDonors(
        charityId: Int,
        page: Int,
        userId: Int,
        projectId: Int
    ) {
        error = null
        state.set(STATE_DONORS_USER_KEY, userId)
        state.set(STATE_DONORS_CHARITY_KEY, charityId)
        state.set(STATE_DONORS_PROJECT_KEY, projectId)
        charityRepository.getCharityDonors(
            charityId = charityId,
            page = page,
            projectId = projectId,
            userId = if (showUserDonations) userId else null,
        ).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    loading = false
                    val tmp = ArrayList(charityDonors)
                    tmp.addAll(state.data)
                    charityDonors = tmp
                }
                is DataState.Error -> {
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onProfileIconClicked(charityId: Int, userId: Int, projectId: Int) {
        showUserDonations = !showUserDonations
        state.set(STATE_DONORS_FILTER_KEY, showUserDonations)
        reset()
        getCharityDonors(charityId, page, userId, projectId)
    }

    fun nextPage(charityId: Int, userId: Int, projectId: Int) {
        //preventing recomposing so it would call pagination more times
        if ((itemPosition + 1) >= (page * DONORS_PAGE_SIZE)) {
            setPageValue(page + 1)
            if (page > 1) {
                getCharityDonors(charityId, page, userId, projectId)
            }
        }
    }

    fun retry(charityId: Int, userId: Int, projectId: Int) {
        reset()
        getCharityDonors(charityId, page, userId, projectId)
    }

    private fun reset() {
        setPageValue(1)
        charityDonors = listOf()
    }

    fun setPageValue(value: Int) {
        page = value
        state.set(STATE_DONORS_PAGE_KEY, page)
    }

    fun setPosition(
        value: Int,
        scrollPosition: Int,
        scrollOffset: Int,
    ) {
        itemPosition = value
        state.set(STATE_DONORS_POSITION_KEY, itemPosition)
        this.scrollPosition = scrollPosition
        this.scrollOffset = scrollOffset
    }


    private fun restoreState() {
        loading = true
        val results: MutableList<Donor> = mutableListOf()
        viewModelScope.launch {
            repeat(page) {
                charityRepository.getCharityDonors(
                    charityId = charityId!!,
                    projectId = projectId ?: -1,
                    userId = if (showUserDonations) userId else null,
                    page = it + 1,
                ).collect { state ->
                    when (state) {
                        is DataState.Success -> {
                            results.addAll(state.data)
                        }
                        else -> { }
                    }
                }
                if(it + 1 == page ){
                    savedPosition = Pair(scrollPosition, scrollOffset)
                    loading = false
                    charityDonors = results
                }
            }
        }
    }
}