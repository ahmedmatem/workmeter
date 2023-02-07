package com.ahmedmatem.android.workmeter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.local.site.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.remote.site.SiteRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class SiteRepository {
    private val localDataSource: SiteLocalDataSource by inject(SiteLocalDataSource::class.java)
    private val remoteDataSource: SiteRemoteDataSource by inject(SiteRemoteDataSource::class.java)

    val sites: LiveData<List<Site>> = localDataSource.getSites()

    suspend fun refreshSites(user: LoggedInUser) {
        withContext(Dispatchers.IO) {
            remoteDataSource.loadSitesForUser(user)
                .catch {
                    // TODO: Catch Error on loading user sites
                }
                .collect { result: Result<List<Site>> ->
                    if (result is Result.Success) {
                        localDataSource.save(*result.data.toTypedArray())
                    } else {
                        result as Result.Error
                        Log.d(
                            TAG,
                            "An exception occurred refreshing sites: ${result.exception}"
                        )
                    }
                }
        }
    }

    companion object {
        const val TAG: String = "SiteRepository"
    }
}