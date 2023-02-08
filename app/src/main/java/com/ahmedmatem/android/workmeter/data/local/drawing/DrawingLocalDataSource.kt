package com.ahmedmatem.android.workmeter.data.local.drawing

import com.ahmedmatem.android.workmeter.data.model.Drawing
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.inject

class DrawingLocalDataSource {
    private val drawingDao: DrawingDao by inject(DrawingDao::class.java)

    fun getDrawings(siteId: String) : Flow<List<Drawing>> {
        return drawingDao.getDrawings(siteId)
    }

    fun insert(vararg drawings: Drawing) {
        drawingDao.insert(*drawings)
    }
}