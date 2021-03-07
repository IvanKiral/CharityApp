package com.kiral.charityapp.ui.badges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Utils
import com.kiral.charityapp.utils.toGrayscale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BadgesFragment : Fragment() {
    private val viewModel: BadgesViewModel by viewModels()
    private val args: BadgesFragmentArgs by navArgs()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.getBadges(args.badges)
            setContent {
                BadgeScreen()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun BadgeScreen() {
        val scrollState = rememberScrollState()
        CharityTheme {
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Text(
                    text = "Your badges",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(16.dp)
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                )
                viewModel.badges.value.chunked(2).forEach {
                    RowBadges(badges = it)
                }
            }
        }
    }

    @Composable
    fun RowBadges(
        badges: List<Badge>,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            badges.forEach { badge ->
                BadgeCard(
                    badge = badge
                )
            }
        }
    }

    @Composable
    fun BadgeCard(
        badge: Badge,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .width(144.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val img = Utils.loadPicture(drawable = badge.iconId)
            img.value?.let {
                Image(
                    bitmap = if (badge.active) it.toGrayscale()
                        .asImageBitmap() else it.asImageBitmap(),
                    contentDescription = "badge icon",
                    modifier = Modifier.size(128.dp)
                )
            }
            Text(
                text = badge.title,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}