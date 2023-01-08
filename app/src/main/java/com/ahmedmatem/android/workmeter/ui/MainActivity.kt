package com.ahmedmatem.android.workmeter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.ui.login.LOGGED_IN_USER_KEY

class MainActivity : AppCompatActivity() {
    private var loggedInUser: LoggedInUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loggedInUser = intent.extras?.getParcelable<LoggedInUser>(LOGGED_IN_USER_KEY)
    }
}