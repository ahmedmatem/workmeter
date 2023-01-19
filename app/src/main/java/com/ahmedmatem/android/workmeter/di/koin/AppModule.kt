package com.ahmedmatem.android.workmeter.di.koin

import com.ahmedmatem.android.workmeter.data.local.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.local.login.UserDao
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.local.site.SiteDao
import com.ahmedmatem.android.workmeter.data.local.site.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.local.worksheet.WorksheetDao
import com.ahmedmatem.android.workmeter.data.local.worksheet.WorksheetLocalDataSource
import com.ahmedmatem.android.workmeter.data.remote.site.SiteRemoteDataSource
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import com.ahmedmatem.android.workmeter.ui.worksheet.WorksheetViewModel
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
    single<WorksheetDao> {
        val db = get<WorkmeterDb>()
        db.worksheetDao
    }

    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }

    // data sources
    single { SiteRemoteDataSource() }
    single { SiteLocalDataSource() }
    single { WorksheetLocalDataSource() }

    // repositories
    single { SiteRepository() }
    single { WorksheetRepository() }
}