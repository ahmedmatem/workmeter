package com.ahmedmatem.android.workmeter.ui.login

import com.ahmedmatem.android.workmeter.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
)