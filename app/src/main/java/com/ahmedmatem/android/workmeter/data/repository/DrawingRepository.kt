package com.ahmedmatem.android.workmeter.data.repository

import com.ahmedmatem.android.workmeter.data.local.drawing.DrawingLocalDataSource
import com.ahmedmatem.android.workmeter.data.remote.drawing.DrawingRemoteDataSource
import org.koin.java.KoinJavaComponent.inject
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.Drawing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DrawingRepository {
    private val localDataSource: DrawingLocalDataSource by inject(DrawingLocalDataSource::class.java)
    private val remoteDataSource: DrawingRemoteDataSource by inject(DrawingRemoteDataSource::class.java)
    private val coroutineContext: CoroutineContext = Dispatchers.IO

    suspend fun refreshDrawings(username: String) {
        withContext(coroutineContext) {
            when (val result: Result<List<Drawing>> = remoteDataSource.loadDrawings(username)) {
                is Result.Success -> {
                    val drawings = result.data.toTypedArray()
                    localDataSource.insert(*drawings)
                }
                is Result.Error -> {
                    // TODO: Catch Error on refreshing drawings
                }
            }
        }
    }

    fun getDrawings(siteId: String): Flow<List<Drawing>> = flow {
        localDataSource.getDrawings(siteId)
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }
}