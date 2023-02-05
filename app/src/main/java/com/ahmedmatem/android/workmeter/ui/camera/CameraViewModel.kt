package com.ahmedmatem.android.workmeter.ui.camera

import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class CameraViewModel : BaseViewModel() {
    private val worksheetRepository: WorksheetRepository by inject(WorksheetRepository::class.java)

    private val _isCameraShutterVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCameraShutterVisible = _isCameraShutterVisible.asStateFlow()

    fun savePhoto(id: String, photoUri: String){
        viewModelScope.launch {
            worksheetRepository.savePhoto(id, photoUri)
        }
    }

    fun runCaptureEffect(durationInMillis: Long) {
        viewModelScope.launch {
            _isCameraShutterVisible.value = true
            delay(durationInMillis)
            _isCameraShutterVisible.value = false
        }
    }
}