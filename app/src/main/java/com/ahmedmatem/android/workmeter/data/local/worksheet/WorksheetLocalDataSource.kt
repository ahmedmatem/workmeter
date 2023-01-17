package com.ahmedmatem.android.workmeter.data.local.worksheet

import com.ahmedmatem.android.workmeter.data.model.Worksheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class WorksheetLocalDataSource {
    private val dao: WorksheetDao by inject(WorksheetDao::class.java)

    fun save(worksheet: Worksheet){
        dao.insert(worksheet)
    }

    /**
     * Get the count of all worksheets in site by given siteId
     */
    suspend fun count(siteId: String) = withContext(Dispatchers.IO){
        dao.count(siteId)
    }
}