package com.kiral.charityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.theme.CharityTheme

class CreditFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme{
                    CreditScreen(
                        onBackPressed = requireActivity()::onBackPressed
                    )
                }
            }
        }
    }
}

@Composable
fun CreditScreen(
    onBackPressed: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = stringResource(R.string.back_icon_description),
                onIconClicked = onBackPressed,
                size = 18.dp
            )
            Text(
                text = stringResource(R.string.credit_title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        Text(
            stringResource(R.string.credit_freepik),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}