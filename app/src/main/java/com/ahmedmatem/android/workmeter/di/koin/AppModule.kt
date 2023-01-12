package com.ahmedmatem.android.workmeter.di.koin

import com.ahmedmatem.android.workmeter.data.local.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.local.login.UserDao
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.local.site.SiteDao
import com.ahmedmatem.android.workmeter.data.local.site.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.remote.site.SiteRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // database
    single { WorkmeterDb.getInstance(androidContext()) }

    // daos
    single<UserDao> {
        val db = get<WorkmeterDb>()
        db.userDao
    }
    single<SiteDao> {
        val db = get<WorkmeterDb>()
        db.siteDao
    }

    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }

    // data sources
    single { SiteRemoteDataSource() }
    single { SiteLocalDataSource() }

    // repositories
    single { SiteRepository() }

}