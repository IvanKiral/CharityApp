package com.kiral.charityapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.ProfileIconBorder
import com.kiral.charityapp.ui.theme.cardTextStyle
import com.kiral.charityapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class CharitiesScreen {
    Charities, Ranking
}

@AndroidEntryPoint
class CharitiesFragment : Fragment() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val viewModel: CharitiesViewModel by viewModels()
    private val args: CharitiesFragmentArgs by navArgs()
    var email: String? = null

    var userId: Int = -1

    val USER_ID = intPreferencesKey("user_id")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    suspend fun write_id(id: Int) {
        dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val auth0 = Auth0(requireContext())
        val apiClient = AuthenticationAPIClient(auth0)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(requireContext()))

        manager.getCredentials(object: Callback<Credentials, CredentialsManagerException> {
            override fun onSuccess(result: Credentials) {
                /*Log.i("CharitiesFragmentID", credentials.toString())
                val uId: Flow<Int> = dataStore.data
                    .map { preferences ->
                        // No type safety.
                        preferences[USER_ID] ?: -1
                    }
                uId.asLiveData().observe(viewLifecycleOwner){
                    if(it != null){
                        if(it != -1){
                            viewModel.getCharities(it, "svk")
                        }
                        else {
                            showUserProfile(auth0, credentials.accessToken)
                        }
                    }
                }*/

                showUserProfile(auth0, result.accessToken)
            }
            override fun onFailure(error: CredentialsManagerException) {
                // No credentials were previously saved or they couldn't be refreshed
            }
        })

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
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                CharityAppBar(
                    tabSelected = tabSelected,
                    modifier = Modifier.fillMaxWidth(),
                    onProfileClick = {
                        val action = CharitiesFragmentDirections.actionCharitiesFragmentToProfileFragment(userId)
                        findNavController().navigate(action)
                    },
                    onTabSelected = { tabSelected = it }
                )

                when (tabSelected) {
                    CharitiesScreen.Charities -> GridCharity(
                            lst = viewModel.charities.value,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .align(Alignment.CenterHorizontally)
                    )
                    CharitiesScreen.Ranking -> RankingScreen()
                }
            }
        }
    }

    @Composable
    fun RankingScreen() {
    }

    @ExperimentalFoundationApi
    @Composable
    fun GridCharity(
        lst: List<CharityListItem>,
        modifier: Modifier = Modifier
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(lst) {
                CharityItem(
                    charity = it,
                    onClick = {
                        val action = CharitiesFragmentDirections
                            .actionCharitiesFragmentToCharityDetailFragment(it.id, userId)
                        findNavController()
                            .navigate(action)
                    }
                )
            }
        }
    }

    @Composable
    fun CharityItem(
        charity: CharityListItem,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .clickable(onClick = onClick)
        ) {
            charity.imgSrc.let { url ->
                val image = loadPicture(url = url, defaultImage = R.drawable.ic_loading_photo).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(110.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                }

            }

            Text(
                text = charity.name,
                style = cardTextStyle,
                maxLines = 2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    private fun showUserProfile(account: Auth0, accessToken: String) {
        val client = AuthenticationAPIClient(account)

        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.i("CharitiesFragment", "Somethings wrong")
                    // Something went wrong!
                }

                override fun onSuccess(result: UserProfile) {
                    email = result.email
                    userId = viewModel.getId(email!!)
                    Log.i("CharitiesFragment", "inShowUser")
                    /*lifecycleScope.launch {
                        write_id(userId!!)
                    }*/

                    viewModel.getCharities(userId, "svk")
                }
            })
    }
}

@Composable
fun CharityAppBar(
    tabSelected: CharitiesScreen,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onTabSelected: (CharitiesScreen) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tabs(
            modifier = Modifier.weight(0.7f),
            titles = listOf(
                stringResource(R.string.CharitiesFragment_Charities),
                stringResource(R.string.CharitiesFragment_Rankings)
            ),
            tabSelected = tabSelected,
            onTabSelected = onTabSelected
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            IconRoundCorner(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd),
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
                .preferredSize(56.dp)
                .border(width = 1.dp, color = ProfileIconBorder, shape = CircleShape)
                .clip(shape = CircleShape)
                .clickable(onClick = onClick)
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "",
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
                    )
                }
            }
        }
    }



}