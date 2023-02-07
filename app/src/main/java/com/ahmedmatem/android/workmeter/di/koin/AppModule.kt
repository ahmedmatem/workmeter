package com.ahmedmatem.android.workmeter.di.koin

import com.ahmedmatem.android.workmeter.data.local.WorkmeterDb
import com.ahmedmatem.android.workmeter.data.local.drawing.DrawingDao
import com.ahmedmatem.android.workmeter.data.local.drawing.DrawingLocalDataSource
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.local.site.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.local.worksheet.WorksheetLocalDataSource
import com.ahmedmatem.android.workmeter.data.remote.drawing.DrawingRemoteDataSource
import com.ahmedmatem.android.workmeter.data.remote.site.SiteRemoteDataSource
import com.ahmedmatem.android.workmeter.data.repository.DrawingRepository
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // database
    single { WorkmeterDb.getInstance(androidContext()) }

    // daos
    single {
        val db = get<WorkmeterDb>()
        db.userDao
    }
    single {
        val db = get<WorkmeterDb>()
        db.siteDao
    }
    single {
        val db = get<WorkmeterDb>()
        db.worksheetDao
    }
    single {
        val db = get<WorkmeterDb>()
        db.drawingDao
    }

    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }
    single { Firebase.storage }

    // data sources
    single { SiteRemoteDataSource() }
    single { DrawingRemoteDataSource() }
    single { SiteLocalDataSource() }
    single { WorksheetLocalDataSource() }
    single { DrawingLocalDataSource() }

    // repositories
    single { SiteRepository() }
    single { WorksheetRepository() }
    single { DrawingRepository() }
}