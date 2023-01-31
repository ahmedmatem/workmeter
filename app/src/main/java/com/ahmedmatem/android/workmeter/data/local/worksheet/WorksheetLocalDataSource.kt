package com.ahmedmatem.android.workmeter.data.local.worksheet

import com.ahmedmatem.android.workmeter.data.model.Worksheet
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.inject

class WorksheetLocalDataSource {
    private val dao: WorksheetDao by inject(WorksheetDao::class.java)

    fun getById(id: String) : Worksheet {
        return dao.getById(id)
    }

    fun save(worksheet: Worksheet){
        dao.insert(worksheet)
    }

    fun getWorksheetNumber(siteId: String) : Int {
        return dao.count(siteId)
    }

    fun getAllIncomplete(siteId: String) : Flow<List<com.ahmedmatem.android.workmeter.data.model.Worksheet>> {
        return dao.allIncomplete(siteId)
    }

    fun savePhoto(id: String, photoUri: String) {
        val currentPhotos = dao.getById(id).photos
        // append photoUri to current photos
        val newPhotos = if(currentPhotos.isNotBlank())
            "$currentPhotos,$photoUri"
        else
            photoUri
        dao.updatePhotos(id, newPhotos)
    }
}