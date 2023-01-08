package com.ahmedmatem.android.workmeter.di.koin

import androidx.navigation.navArgument
import com.ahmedmatem.android.workmeter.data.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.login.local.UserDao
import com.ahmedmatem.android.workmeter.data.site.SiteRepository
import com.ahmedmatem.android.workmeter.data.site.local.SiteDao
import com.ahmedmatem.android.workmeter.data.site.local.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.site.remote.SiteRemoteDataSource
import com.ahmedmatem.android.workmeter.ui.sites.SiteListFragmentArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
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