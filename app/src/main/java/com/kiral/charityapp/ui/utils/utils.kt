package com.kiral.charityapp.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun buildInformationText(
    peopleDonated: Int,
    donorDonated: Double,
    text: String
): AnnotatedString {
    return buildAnnotatedString {
        if (peopleDonated != 0) {
            append(peopleDonated.toString() + " people ")
            if (donorDonated > 0) {
                append("and ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("you ")
                }
            }
            append("have ")
        } else {
            if (donorDonated > 0) {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("You have ")
                }
            }
            else{
                append("No one has yet")
            }
        }
        append(text)
    }
}