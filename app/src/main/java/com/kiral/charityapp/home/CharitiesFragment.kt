package com.kiral.charityapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.AmbientConfiguration
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.kiral.charityapp.R
import com.kiral.charityapp.theme.CharityTheme
import com.kiral.charityapp.theme.cardTextStyle
import com.kiral.charityapp.theme.circleBorder
import kotlin.math.ceil

enum class CharitiesScreen {
    Charities, Ranking
}

data class CharityGridItem(
    val imageUrl: Int,
    val text: String
)


class CharitiesFragment : Fragment() {

    val data = listOf(
        CharityGridItem(imageUrl = R.drawable.children, text = "Domov mladeze krasna horka"),
        CharityGridItem(imageUrl = R.drawable.children, text = "Domov mladeze krasna horka"),
        CharityGridItem(imageUrl = R.drawable.children, text = "Domov mladeze krasna horka"),
    )

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharitiesScreen()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun CharitiesScreen() {
        CharityTheme {
            var tabSelected by remember { mutableStateOf(CharitiesScreen.Charities) }
            Column {
                CharityAppBar(
                    tabSelected = tabSelected,
                    modifier = Modifier.fillMaxWidth(),
                    onProfileClick = {
                        findNavController().navigate(R.id.action_charitiesFragment_to_profileFragment)
                    },
                    onTabSelected = { tabSelected = it }
                )

                when (tabSelected) {
                    CharitiesScreen.Charities -> GridCharity(
                        lst = data,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    CharitiesScreen.Ranking -> RankingScreen()
                }
//                StaggeredVerticalGrid(maxColumnWidth = 220.dp) {
//                    for(item in data){
//                        CharityItem(
//                            imageBitmap = imageResource(id = item.imageUrl),
//                            text = item.text
//                        )
//                    }
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun GridCharity(
        lst: List<CharityGridItem>,
        modifier: Modifier = Modifier
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(lst) {
                CharityItem(charity = it)
            }
        }
    }

    @Composable
    fun CharityItem(
        charity: CharityGridItem,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .clickable(onClick = {findNavController().navigate(R.id.action_charitiesFragment_to_charityDetailFragment)})
        ) {
            Image(
                bitmap = imageResource(id = charity.imageUrl),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(107.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Text(
                charity.text,
                style = cardTextStyle,
                maxLines = 2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }

}

@Composable
fun RankingScreen() {

}

@Composable
fun CharityAppBar(
    tabSelected: CharitiesScreen,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onTabSelected: (CharitiesScreen) -> Unit
) {
    Row(
        modifier = modifier.padding(top = 20.dp, start = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tabs(
            modifier = Modifier.weight(0.7f),
            titles = CharitiesScreen.values().map { it.name },
            tabSelected = tabSelected,
            onTabSelected = onTabSelected
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            IconRoundCorner(modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .padding(end = 20.dp),
                imageVector = vectorResource(id = R.drawable.ic_profile),
                onClick = onProfileClick
            )
        }
    }
}

@Composable
fun IconRoundCorner(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                //.padding(end = 32.dp)
                .preferredSize(56.dp)
                .border(width = 1.dp, color = circleBorder, shape = CircleShape)
                .clip(shape = CircleShape)
                .clickable(onClick = onClick)
        ) {
            Image(
                imageVector = imageVector,
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: CharitiesScreen,
    onTabSelected: (CharitiesScreen) -> Unit
) {
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier,
        contentColor = Color.Black,
        backgroundColor = Color.White,
        indicator = { },
        divider = { },
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal

            Tab(
                selected = selected,
                onClick = { onTabSelected(CharitiesScreen.values()[index]) },
            ) {
                Column() {
                    Text(
                        modifier = if (selected) Modifier else Modifier.padding(top = 4.dp),
                        style = if (selected) MaterialTheme.typography.h5
                        else MaterialTheme.typography.h5.copy(
                            fontSize = 20.sp,
                            color = Color.Black.copy(alpha = 0.3f)
                        ),
                        text = title,
                        //textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}


//@Composable
//fun CharityGrid(items: List<CharityGridItem>){
//    Lazy(content){
//        val x = items.chunked(2)
//        for(item in x) {
//            CharityRow(items = item)
//        }
//    }
//}


//@Composable
//fun CharityRow(
//    items: List<CharityGridItem>
//){
//    Row(Modifier.padding(top = 20.dp)){
//        for(i in 0 until items.size) {
//            CharityItem(
//                imageBitmap = imageResource(id =  items[i].imageUrl),
//                text = items[i].text,
//                modifier = if(i == 0) Modifier.padding(end = 6.dp).weight(1f)
//                else Modifier.weight(1f)
//            )
//        }
//    }
//}


@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        //val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / 2
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(2) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(2) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}


private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
