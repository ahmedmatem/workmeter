package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Size
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorksheetViewModel : BaseViewModel() {

    private val repository: WorksheetRepository by inject(WorksheetRepository::class.java)

    private val _worksheetState: MutableStateFlow<Worksheet?> = MutableStateFlow(null)
    val worksheetState: StateFlow<Worksheet?> = _worksheetState

    init {
        // TODO: initialize worksheet
        initWorksheet()
    }

    fun save(){
        viewModelScope.launch {
            repository.save(_worksheetState.value!!)
        }
    }

    private fun initWorksheet() {
        _worksheetState.value = Worksheet(
            id = "123",
            siteId = "1231313",
            sealNum = 25,
            drawingUrl = "",
            location = "",
            size = Size(-6, -3).toString()
        )
        // TODO: get next seal number from local database
        viewModelScope.launch {
            repository.sealNumber("1231313")
                .collect{
                    _worksheetState.value = Worksheet(
                        id = "123",
                        siteId = "1231313",
                        sealNum = it + 1,
                        drawingUrl = "",
                        location = "",
                        size = Size(-6, -3).toString()
                    )
                }
        }
    }
}