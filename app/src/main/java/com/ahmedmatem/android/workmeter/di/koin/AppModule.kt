package com.ahmedmatem.android.workmeter.di.koin

import com.ahmedmatem.android.workmeter.data.WorkmeterDb
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { WorkmeterDb.getInstance(androidContext()) }
    single { FirebaseAuth.getInstance() }

}