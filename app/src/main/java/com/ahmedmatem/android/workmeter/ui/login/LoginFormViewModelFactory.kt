package com.ahmedmatem.android.workmeter.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedmatem.android.workmeter.data.local.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.local.login.LoginLocalDataSource
import com.ahmedmatem.android.workmeter.data.repository.LoginRepository
import com.ahmedmatem.android.workmeter.data.remote.login.LoginRemoteDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginFormViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFormViewModel::class.java)) {
            return LoginFormViewModel(
                loginRepository = LoginRepository(
                    localDataSource = LoginLocalDataSource(
                        userDao = WorkmeterDb.getInstance(context = context).userDao
                    ),
                    remoteDataSource = LoginRemoteDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}