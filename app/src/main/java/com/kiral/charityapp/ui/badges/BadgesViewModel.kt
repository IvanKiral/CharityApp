package com.kiral.charityapp.ui.badges

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.utils.Constants.BADGES
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BadgesViewModel
@Inject
constructor() : ViewModel() {
    var badges by mutableStateOf<List<Badge>>(listOf())
        private set

    fun getBadges(badgesId: IntArray) {
        val lst = mutableListOf<Badge>()
        BADGES.forEach { (id, value) ->
            lst.add(
                Badge(
                    id = id,
                    stringId = value.stringId,
                    active = !badgesId.contains(id),
                    iconId = value.icon
                )
            )
        }
        lst.sortBy { it.active }
        badges = lst
    }
}