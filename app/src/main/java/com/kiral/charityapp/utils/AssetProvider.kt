package com.kiral.charityapp.utils

import com.kiral.charityapp.R
import com.kiral.charityapp.ui.BaseApplication

class AssetProvider
constructor(
    private val context: BaseApplication
){
    fun networkError() = context.resources.getString(R.string.network_error)

    fun networkRegisterError() = context.resources.getString(R.string.network_registerError)

    fun userNotFound() = context.resources.getString(R.string.network_userNotFound)

    fun networkPaymentError() = context.resources.getString(R.string.network_payment_error)
}