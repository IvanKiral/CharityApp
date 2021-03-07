package com.kiral.charityapp.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.ui.theme.ButtonBlue
import com.kiral.charityapp.ui.theme.DividerColor
import com.kiral.charityapp.ui.theme.TextOptionSubtitle
import com.kiral.charityapp.ui.theme.TextOptionTitle
import com.kiral.charityapp.utils.Convert
import java.util.*

@Composable
fun OptionsMenu(
    modifier: Modifier = Modifier,
    regularDonationValue: Double,
    regularDonationFrequency: Int,
    region: String,
    isSwitched: Boolean,
    setCountryDialog: (Boolean) -> Unit,
    switchFunction: (Boolean) -> Unit,
    setDonationDialog: (Boolean) -> Unit,
    logout: () -> Unit
) {
    Column(
        modifier = modifier
    ) {

        Option(
            title = stringResource(R.string.ProfileFragment_RegularDonations),
            description = "${regularDonationValue.Convert()} €/${DonationFrequency.values()[regularDonationFrequency]}",
            hasSwitch = true,
            isSwitched = isSwitched,
            switchFunction = switchFunction,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                setDonationDialog(true)
            }
        )
        Option(
            title = "Select charity types",
            description = "",
            hasSwitch = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
        Option(
            title = "Select country",
            description = Locale("", region).displayCountry,
            hasSwitch = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                setCountryDialog(true)
            }
        )
        Option(
            title = stringResource(R.string.ProfileFragment_Logout),
            description = stringResource(R.string.ProfileFragment_LogoutDescription),
            hasSwitch = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                logout()
            }
        )
        Divider(
            thickness = 1.dp,
            color = DividerColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Option(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isSwitched: Boolean = false,
    hasSwitch: Boolean = false,
    onClick: () -> Unit = {},
    switchFunction: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .clickable(onClick = onClick)
    ) {
        Divider(
            thickness = 1.dp,
            color = DividerColor,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6.copy(color = TextOptionTitle)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            if (hasSwitch) {
                Switch(
                    checked = isSwitched,
                    onCheckedChange = switchFunction,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = SwitchDefaults.colors(checkedThumbColor = ButtonBlue)
                )
            }
        }
    }
}