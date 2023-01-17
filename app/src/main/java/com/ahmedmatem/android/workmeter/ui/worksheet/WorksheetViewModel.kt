package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Size
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorksheetViewModel : BaseViewModel() {
    // TODO: read worksheet from local database use StateFlow
    val worksheet: Worksheet = Worksheet(
        id = "123",
        siteId = "1231313",
        sealNum = 4,
        drawingUrl = "",
        location = "",
        size = Size(-6, -3).toString()
    )
    private val repository: WorksheetRepository by inject(WorksheetRepository::class.java)

    fun save(){
        viewModelScope.launch {
            repository.save(worksheet)
        }
    }
}