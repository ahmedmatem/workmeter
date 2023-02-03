package com.ahmedmatem.android.workmeter.data.repository

import com.ahmedmatem.android.workmeter.data.local.worksheet.WorksheetLocalDataSource
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class WorksheetRepository() {
    private val localDataSource: WorksheetLocalDataSource by inject(WorksheetLocalDataSource::class.java)
    private val ioDispatcher = Dispatchers.IO

    suspend fun getWorksheetBy(id: String) : Flow<Worksheet> {
        return flow {
            localDataSource.getById(id)
                .flowOn(ioDispatcher)
                .collect {
                    emit(it)
                }
        }
    }

    suspend fun save(worksheet: Worksheet) {
        withContext(ioDispatcher) {
            localDataSource.save(worksheet)
        }
    }

    suspend fun savePhoto(id: String, photoUri: String) {
        withContext(ioDispatcher) {
            localDataSource.savePhoto(id, photoUri)
        }
    }

    suspend fun deletePhoto (id: String, photoUri: String) {
        withContext(ioDispatcher) {
            localDataSource.deletePhoto(id, photoUri)
        }
    }

    fun getAllIncompleteWorksheets(siteId: String) : Flow<List<Worksheet>> {
        return flow {
            localDataSource.getAllIncomplete(siteId)
                .flowOn(ioDispatcher)
                .collect{
                    emit(it)
                }
        }
    }

    /**
     * Function find the number of all worksheet in the database
     * and add 1 to generate sealNum(seal number).
     */
    suspend fun generateSealNum(siteId: String) : Int {
        return withContext(ioDispatcher) {
            localDataSource.getWorksheetNumber(siteId) + 1
        }
    }
}