package com.ahmedmatem.android.workmeter.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.data.repository.LoginRepository
import com.ahmedmatem.android.workmeter.data.Result

import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginFormViewModel (private val loginRepository: LoginRepository) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(email, password)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(result.data)
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun updateUiWithUser(user: FirebaseUser? = null){
        viewModelScope.launch {
            val loggedInUser = if (user == null) {
                _loginResult.value?.success!!
            } else {
                val userFromLocal = loginRepository.getUserFromLocalDb(user.uid)
                LoggedInUser(user.uid, userFromLocal?.username!!)
            }
            navigationCommand.value = NavigationCommand.To(
                LoginFormFragmentDirections
                    .actionLoginFormFragmentToMainGraph(loggedInUser)
            )
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}