package com.kiral.charityapp.ui.badges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.kiral.charityapp.ui.theme.CharityTheme

class BadgesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BadgeScreen()
            }
        }
    }

    @Composable
    fun BadgeScreen(){
        CharityTheme() {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Badges")
            }
        }
    }
}