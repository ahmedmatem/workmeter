package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Log
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.model.assign
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorksheetViewModel(
    private val siteId: String,
    private var _worksheetId: String? ) : BaseViewModel() {

    private val repository: WorksheetRepository by inject(WorksheetRepository::class.java)

    private var _location: String = ""
    private var _width: String = ""
    private var _height: String = ""

    private val _worksheetState: MutableStateFlow<Worksheet?> = MutableStateFlow(null)
    val worksheetState: StateFlow<Worksheet?> = _worksheetState

    init {
        loadWorksheet()
    }

    fun save(){
        viewModelScope.launch {
            _worksheetState.value =  _worksheetState.value!!
                .assign(_location, _width, _height, _worksheetState.value!!.photos)
            repository.save(_worksheetState.value!!)
        }
    }

    fun deletePhoto(photoUri: String) {
        viewModelScope.launch {
            repository.deletePhoto(_worksheetId!!, photoUri)
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

    fun navigateToCamera() {
        navigationCommand.value = NavigationCommand.To(WorksheetFragmentDirections
            .actionWorksheetFragmentToCameraFragment(_worksheetState.value?.id!!)
        )
    }

    private fun loadWorksheet() {
        viewModelScope.launch {
            _worksheetId?.let{ id ->
                // Load existing worksheet from local database
                repository.getWorksheetBy(id).collect { worksheet ->
                    _worksheetState.value = worksheet
                }
            } ?: run {
                val sealNum = repository.generateSealNum(siteId)
                _worksheetState.value = Worksheet.default(siteId, sealNum)
            }
        }
    }

    companion object {

        /**
         * Factory to construct view model with parameter
         */
        val Factory: ViewModelProvider.Factory =
            @Suppress("UNCHECKED_CAST")
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    val argsBundle = extras[DEFAULT_ARGS_KEY]
                    val siteId = argsBundle?.getString("siteId")!!
                    val worksheetId = argsBundle?.getString("worksheetId")
                    if(modelClass.isAssignableFrom(WorksheetViewModel::class.java)){
                        return WorksheetViewModel(siteId, worksheetId) as T
                    }
                    throw IllegalArgumentException("Unable to construct a viewModel class.")
                }
            }
    }
}
