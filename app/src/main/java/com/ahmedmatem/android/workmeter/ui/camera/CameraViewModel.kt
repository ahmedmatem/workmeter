package com.ahmedmatem.android.workmeter.ui.camera

import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class CameraViewModel : BaseViewModel() {
    private val worksheetRepository: WorksheetRepository by inject(WorksheetRepository::class.java)

    fun savePhoto(id: String, photoUri: String){
        viewModelScope.launch {
            worksheetRepository.savePhoto(id, photoUri)
        }
    }
}