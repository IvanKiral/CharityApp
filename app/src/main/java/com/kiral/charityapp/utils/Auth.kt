package com.kiral.charityapp.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Auth {

    fun withUserEmail(
        account: Auth0,
        accessToken: String,
        onFailFunction: () -> Unit = {},
        onSuccessFunction: (String) -> Unit,
    ) {
        val client = AuthenticationAPIClient(account)
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.i("AuthNavigate", "An error has occured: $error")
                    onFailFunction()
                }

                override fun onSuccess(result: UserProfile) {
                    Log.i("emailtest", "email: ${result.email!!}")
                    onSuccessFunction(result.email!!)
                }
            })
    }

    fun logout(
        account: Auth0,
        context: Context,
        dataStore: DataStore<Preferences>,
        navigate: () -> Unit = {}
    ) {
        val client = AuthenticationAPIClient(account)
        val manager = CredentialsManager(client, SharedPreferencesStorage(context))
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(context, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    manager.clearCredentials()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.edit {
                            it.clear()
                        }
                    }
                    navigate()
                }

                override fun onFailure(error: AuthenticationException) {
                    Log.i("AuthLogout", "An error has occured: $error")
                }
            })
    }
}