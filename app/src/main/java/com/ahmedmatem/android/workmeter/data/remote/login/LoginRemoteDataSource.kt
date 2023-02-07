package com.ahmedmatem.android.workmeter.data.remote.login

import android.content.res.Resources.NotFoundException
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.utils.await
import com.google.firebase.auth.FirebaseAuth
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException

class LoginRemoteDataSource {
    private val auth: FirebaseAuth by inject(FirebaseAuth::class.java)

    suspend fun login(email: String, password: String): Result<LoggedInUser>{
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if(result.user != null){
                Result.Success(LoggedInUser(userId = result.user?.uid!!, displayName = email))
            } else {
                Result.Error(NotFoundException("No user found in the remote db."))
            }
        } catch (e: Exception){
            Result.Error(IOException("Error logging in", e))
        }
    }
}