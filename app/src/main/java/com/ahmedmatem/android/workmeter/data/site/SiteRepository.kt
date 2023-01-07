package com.ahmedmatem.android.workmeter.data.site

import com.ahmedmatem.android.workmeter.data.site.remote.SiteRemoteDataSource
import org.koin.java.KoinJavaComponent.inject

class SiteRepository {
    private val remoteDataSource: SiteRemoteDataSource by inject(SiteRemoteDataSource::class.java)

    fun loadSites(uid: String){
        remoteDataSource.loadSitesForUser(uid)
    }
}