package com.ahmedmatem.android.workmeter.data.local.login

import android.content.res.Resources.NotFoundException
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.model.User
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
                Result.Error(NotFoundException("No user found in the local db."))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    suspend fun getUserBy(uid: String) : User? {
        return userDao.getBy(uid)
    }

    suspend fun save(user: User) {
        userDao.insert(user)
    }

    fun logout() {
        // TODO: revoke authentication
    }
}