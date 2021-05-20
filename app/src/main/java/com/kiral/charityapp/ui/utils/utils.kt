package com.kiral.charityapp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.kiral.charityapp.R
import java.util.*


@Composable
fun buildInformationText(
    peopleDonated: Int,
    donorDonated: Double,
    postfix: String
): AnnotatedString {
    var tmpPeopleDonated  = peopleDonated
    if(donorDonated > 0.0){
        tmpPeopleDonated--
    }

    val has = stringResource(id = R.string.detail_peopleDonated_has)
    val have = stringResource(id = R.string.detail_peopleDonated_have)
    val peopleDonatedZero = stringResource(id = R.string.detail_peopleDonated_prefix_zero)
    val peopleDonatedPrefix = LocalContext.current.resources
        .getQuantityString (
            R.plurals.detail_peopleDonated_prefix,
            tmpPeopleDonated,
            tmpPeopleDonated
        )
    val peopleDonatedAnd = stringResource(id = R.string.detail_peopleDonated_and)
    val peopleDonatedYou = stringResource(id = R.string.detail_peopleDonated_you)



    return buildAnnotatedString {
        when {
            tmpPeopleDonated > 0 -> {
                append("$peopleDonatedPrefix ")
                if (donorDonated > 0) {
                    append("$peopleDonatedAnd ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$peopleDonatedYou ")
                    }
                    append("$have ")
                } else {
                    if(tmpPeopleDonated == 1)
                        append("$has ")
                    else
                        append("$have ")
                }
            }
            else -> {
                if (donorDonated > 0) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${peopleDonatedYou.capitalize(Locale.ROOT)} ")
                    }
                    append("$have ")
                } else {
                    append("$peopleDonatedZero ")
                }
            }
        }
        append(postfix)
    }
}