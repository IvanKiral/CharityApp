package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.ui.components.ProfileImageWithBorder
import com.kiral.charityapp.utils.Utils
import com.kiral.charityapp.utils.convert
import com.kiral.charityapp.utils.makeGravatarLink

@Composable
fun LeaderBoardItem(
    item: LeaderBoardProfile,
    index: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(71.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                index.toString(),
                style = MaterialTheme.typography.body2
            )
            ProfileImageWithBorder(
                imageBitmap = item.email.let { e ->
                    val img = Utils.loadPicture(
                        url = e.makeGravatarLink(),
                        defaultImage = R.drawable.ic_loading_photo
                    )
                    img.value?.asImageBitmap()
                },
                borderMargin = 4.dp,
                imageSize = 48.dp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                item.name,
                style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(start = 16.dp)
            )
            Text(
                text = "${item.donated.convert()}€",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.End
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}