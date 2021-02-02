package com.kiral.charityapp.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.theme.CharityTheme

class SetupRegularPaymentsFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetupPaymentsScreen()
            }
        }
    }

    @Composable
    fun SetupPaymentsScreen(){
        CharityTheme() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.SetupRegularPaymentsFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .preferredHeight(64.dp),
                    onClick = {
                        findNavController()
                            .navigate(R.id.action_setupRegularPaymentsFragment_to_charitiesFragment)
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }

}


