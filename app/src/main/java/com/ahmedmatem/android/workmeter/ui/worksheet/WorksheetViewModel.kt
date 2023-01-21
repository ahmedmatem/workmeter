package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.model.State
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.model.assign
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorksheetViewModel(private val siteId: String) : BaseViewModel() {

    private val repository: WorksheetRepository by inject(WorksheetRepository::class.java)

    private var _location: String = ""
    private var _width: String = "0"
    private var _height: String = "0"

    private val _worksheetState: MutableStateFlow<Worksheet?> = MutableStateFlow(null)
    val worksheetState: StateFlow<Worksheet?> = _worksheetState

    init {
        init()
    }

    fun save(){
        viewModelScope.launch {
            _worksheetState.value = _worksheetState.value!!
                .assign(_location, "${_width}x${_height}")
            repository.save(_worksheetState.value!!)
        }
    }

    fun locationChanged(location: String){
        _location = location
    }

    fun widthChanged(width: String){
        _width = width
    }

    fun heightChanged(height: String) {
        _height = height
    }

    /**
     * Factory to construct view model with parameter
     */
    class Factory(private val siteId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(WorksheetViewModel::class.java)){
                return WorksheetViewModel(siteId) as T
            }
            throw IllegalArgumentException("Unable to construct a viewModel.")
        }
    }

    private fun init() {
        viewModelScope.launch {
            val sealNum = repository.generateSealNum(siteId)
            _worksheetState.value = Worksheet.default(siteId, sealNum)
        }
    }
}