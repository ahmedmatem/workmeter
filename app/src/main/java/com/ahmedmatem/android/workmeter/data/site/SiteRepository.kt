package com.ahmedmatem.android.workmeter.data.site

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ahmedmatem.android.workmeter.data.site.local.Site
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.site.local.SiteLocalDataSource
import com.ahmedmatem.android.workmeter.data.site.remote.SiteRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class SiteRepository {
    private val localDataSource: SiteLocalDataSource by inject(SiteLocalDataSource::class.java)
    private val remoteDataSource: SiteRemoteDataSource by inject(SiteRemoteDataSource::class.java)

    val sites: LiveData<List<Site>> = localDataSource.getSites()

    suspend fun refreshSites(user: LoggedInUser){
        withContext(Dispatchers.IO) {
            remoteDataSource.loadSitesForUser(user)
                .catch {

                }
                .collect { result: Result<List<Site>> ->
                    if (result is Result.Success) {
                        Log.d(TAG, "Refresh site data in local database.")
                        localDataSource.save(*result.data.toTypedArray())
                    } else {
                        result as Result.Error
                        Log.d(TAG, "An exception occurred refreshing sites: ${result.exception}")
                    }
                }
        }
    }

    fun loadSites(user: LoggedInUser): Flow<Result<List<Site>>> {
        return remoteDataSource.loadSitesForUser(user)
    }

    companion object {
        const val TAG: String = "SiteRepository"
    }
}