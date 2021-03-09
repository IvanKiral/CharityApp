package com.kiral.charityapp.ui.badges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.badges.components.BadgesRow
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.theme.CharityTheme
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
                BadgeScreen(
                    viewModel,
                    requireActivity()::onBackPressed
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun BadgeScreen(
    viewModel: BadgesViewModel,
    onBackPressed: () -> Unit
) {
    val scrollState = rememberScrollState()
    CharityTheme {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableIcon(
                    icon = ImageVector.vectorResource(id = R.drawable.ic_back),
                    onIconClicked = onBackPressed,
                    size = 18.dp
                )
                Text(
                    text = "Your badges",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            viewModel.badges.chunked(2).forEach {
                BadgesRow(badges = it)
            }
        }
    }
}