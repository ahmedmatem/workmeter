package com.ahmedmatem.android.workmeter.data.login

import android.util.Log
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.login.local.LoginLocalDataSource
import com.ahmedmatem.android.workmeter.data.login.local.User
import com.ahmedmatem.android.workmeter.data.login.remote.LoginRemoteDataSource
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(
    private val localDataSource: LoginLocalDataSource,
    private val remoteDataSource: LoginRemoteDataSource
) {

    private val coroutineContext: CoroutineContext = Dispatchers.IO

    // in-memory cache of the loggedInUser object
    var loggedInUser: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = loggedInUser != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        loggedInUser = null
    }

    fun logout() {
        loggedInUser = null
        localDataSource.logout()
    }

    suspend fun login(email: String, password: String): Result<LoggedInUser> {
        Log.d("LOGIN", "start local login...")
        return withContext(coroutineContext){
            // local login
            var result = localDataSource.login(email, password)

            when(result){
                is Result.Success -> setLoggedInUser(result.data)
                is Result.Error -> {
                    Log.d("LOGIN", "start remote login... ")
                    // remote login
                    result = remoteDataSource.login(email, password)
                    if(result is Result.Success){
                        setLoggedInUser(result.data)
                        localDataSource.save(user = User(result.data.userId, email, password))
                    }
                }
            }
            return@withContext result
        }
    }

    suspend fun getUserFromLocalDb(uid: String) : User?{
        return localDataSource.getUserBy(uid)
    }

//    suspend fun saveUserInLocalDb(user: User){
//        Log.d("LOGIN", "Save user(${user.uid}) in local database. ")
//        setLoggedInUser(LoggedInUser(userId = user.uid, displayName = user.username))
//        localDataSource.save(user = user)
//    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.loggedInUser = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

//    private fun handleError(error: Result.Error){
//        when(error.exception){
//            is NotFoundException -> {
//                // Start remote login
//            }
//            is IOException -> {}
//            else -> {}
//        }
//    }
}