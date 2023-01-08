package com.ahmedmatem.android.workmeter.data.site

import com.ahmedmatem.android.workmeter.data.site.local.Site
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.site.remote.SiteRemoteDataSource
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.inject

class SiteRepository {
    private val remoteDataSource: SiteRemoteDataSource by inject(SiteRemoteDataSource::class.java)

    fun loadSites(user: LoggedInUser): Flow<Result<List<Site>>> {
        return remoteDataSource.loadSitesForUser(user)
    }
}