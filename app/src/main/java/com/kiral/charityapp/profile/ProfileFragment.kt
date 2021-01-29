package com.kiral.charityapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.kiral.charityapp.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ProfileImageWithBorder(imageBitmap = imageResource(id = R.drawable.rachel), imageSize =128.dp)
            }
        }
    }
}

@Composable
fun ProfileImageWithBorder(
    imageBitmap: ImageBitmap,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    borderMargin: Dp = 12.dp,
    borderWidth: Dp = 2.dp,
){
    Box(
        modifier
            .size(imageSize + borderMargin + borderWidth)
            //.clip(CircleShape)
            .border(
                width = borderWidth,
                color = Color.Black.copy(alpha = 0.10f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center

    ){
        Image(
            bitmap = imageBitmap,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)

        )
    }

}

