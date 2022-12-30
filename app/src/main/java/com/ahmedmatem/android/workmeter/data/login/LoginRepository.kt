package com.ahmedmatem.android.workmeter.data.login

import android.util.Log
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.login.local.LoginLocalDataSource
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val localDataSource: LoginLocalDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        localDataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = localDataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        } else if(result is Result.Error){
            Log.d("DEBUG", "login: ${result.exception.message}")
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}