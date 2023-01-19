package com.ahmedmatem.android.workmeter.data.repository

import com.ahmedmatem.android.workmeter.data.local.worksheet.WorksheetLocalDataSource
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class WorksheetRepository {
    private val localDataSource: WorksheetLocalDataSource by inject(WorksheetLocalDataSource::class.java)
    private val ioDispatcher = Dispatchers.IO

    suspend fun save(worksheet: Worksheet){
        withContext(ioDispatcher){
            localDataSource.save(worksheet)
        }
    }

//    suspend fun sealNumber(siteId: String): Flow<Int> {
//        return flow {
//            localDataSource.count(siteId)
//                .flowOn(ioDispatcher)
//                .collect{
//                    emit(it)
//                }
//        }
//    }

    suspend fun getSealNumber(siteId: String) : Int {
        return withContext(ioDispatcher){
            localDataSource.getWorksheetNumber(siteId)
        }
    }
}