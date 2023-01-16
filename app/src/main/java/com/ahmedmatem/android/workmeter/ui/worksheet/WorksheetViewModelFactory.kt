package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository

class WorksheetViewModelFactory(private val repository: WorksheetRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WorksheetViewModel::class.java)){
            return modelClass as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}