package com.ahmedmatem.android.workmeter.di.koin

import com.ahmedmatem.android.workmeter.data.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.site.SiteRepository
import com.ahmedmatem.android.workmeter.data.site.remote.SiteRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { WorkmeterDb.getInstance(androidContext()) }

    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }

    // data sources
    single { SiteRemoteDataSource() }

    // repositories
    single { SiteRepository() }

}