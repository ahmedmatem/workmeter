package com.ahmedmatem.android.workmeter.data.login.local

import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginLocalDataSource(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val user = userDao.getBy(username, password)

            if(user != null) {
                Result.Success(LoggedInUser(user.uid, user.username))
            } else {
                Result.Error(NullPointerException("No user found in the local db."))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}