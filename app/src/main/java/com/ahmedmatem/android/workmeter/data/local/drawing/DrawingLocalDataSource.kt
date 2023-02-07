package com.ahmedmatem.android.workmeter.data.local.drawing

import com.ahmedmatem.android.workmeter.data.model.Drawing
import org.koin.java.KoinJavaComponent.inject

class DrawingLocalDataSource {
    private val drawingDao: DrawingDao by inject(DrawingDao::class.java)

    fun getSiteDrawings(siteId: String) : List<Drawing> {
        return drawingDao.getSiteDrawings(siteId)
    }

    fun insert(vararg drawings: Drawing) {
        drawingDao.insert(*drawings)
    }
}