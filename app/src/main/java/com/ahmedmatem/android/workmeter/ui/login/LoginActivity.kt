package com.ahmedmatem.android.workmeter.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.ahmedmatem.android.workmeter.databinding.ActivityLoginBinding

import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.data.login.local.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user != null){
            Log.d("DEBUG", "onStart: current user is ${user.uid}")
            // TODO: reload
            // reload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(applicationContext))
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                Log.d("LOGIN", "Result of login - Error(${loginResult.error})")
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                Log.d("LOGIN", "Login success... ")
                updateUiWithUser(loginResult.success)
            }

            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
//            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

//    private suspend fun remoteLogin(email: String, password: String){
//        return try {
//            val result = auth.signInWithEmailAndPassword(email, password).await()
//            loginViewModel.saveUserInLocalDb(User(result.user!!.uid, email, password))
//        } catch (e: Exception){
//            showLoginFailed(R.string.login_failed)
//        }
//    }

//    private fun loginRemote(email: String, password: String){
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                Log.d("LOGIN", "Remote login is succeeded. ")
//                val fbUser = auth.currentUser
//                if(fbUser != null) {
//                    val user = User(uid = fbUser.uid, username = email, password = password)
//                    loginViewModel.saveUserInLocalDb(user)
//                    updateUiWithUser(LoggedInUserView(displayName = user.username))
//                }
//            } else {
//                Log.d("LOGIN", "Remote login failed... ")
//                Log.d("LOGIN", "Exception: ${task.exception?.message} ")
//                Log.d("LOGIN", "Result: ${task.result.toString()} ")
//                // If login fails, display a message to the user
//                showLoginFailed(R.string.login_failed)
//            }
//        }
//    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}