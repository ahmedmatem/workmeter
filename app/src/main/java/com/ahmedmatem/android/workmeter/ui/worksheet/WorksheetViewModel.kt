package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.model.assign
import com.ahmedmatem.android.workmeter.data.repository.DrawingRepository
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WorksheetViewModel(
    private val siteId: String,
    private var _worksheetId: String?,
    private val loggedInUser: LoggedInUser
) : BaseViewModel() {

    private val worksheetRepository: WorksheetRepository by inject(WorksheetRepository::class.java)
    private val drawingRepository: DrawingRepository by inject(DrawingRepository::class.java)

    private var _location: String = ""
    private var _width: String = ""
    private var _height: String = ""

    private val _drawings: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val drawings: StateFlow<List<String>> = _drawings.asStateFlow()

    private val _worksheetState: MutableStateFlow<Worksheet?> = MutableStateFlow(null)
    val worksheetState: StateFlow<Worksheet?> = _worksheetState

    init {
        loadWorksheet()
        loadDrawings(siteId)
    }

    fun save(){
        viewModelScope.launch {
            _worksheetState.value =  _worksheetState.value!!
                .assign(_location, _width, _height, _worksheetState.value!!.photos)
            worksheetRepository.save(_worksheetState.value!!)
        }
    }

    fun deletePhoto(photoUri: String) {
        viewModelScope.launch {
            worksheetRepository.deletePhoto(_worksheetId!!, photoUri)
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
            /**
             * If worksheet is not created yet - Create default worksheet and save it in local db.
             */
            if(_worksheetId == null) {
                val sealNum = worksheetRepository.generateSealNum(siteId)
                val default = Worksheet.default(siteId, sealNum)
                _worksheetId = default.id
                worksheetRepository.save(default)
            }
            /**
             * Bind worksheet state to changes in local db
             */
            worksheetRepository.getWorksheetBy(_worksheetId!!).collect { worksheet ->
                _worksheetState.value = worksheet
            }
        }
    }

    private fun loadDrawings(siteId: String) {
        viewModelScope.launch {
            drawingRepository.getDrawings(siteId)
                .map { drawingList ->
                    drawingList.mapNotNull { drawing ->
                        drawing.name
                    }
                }
                .collect {
                    _drawings.value = it
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
                    val loggedInUser = argsBundle.getParcelable<LoggedInUser>("loggedInUserArgs")!!
                    if(modelClass.isAssignableFrom(WorksheetViewModel::class.java)){
                        return WorksheetViewModel(siteId, worksheetId, loggedInUser) as T
                    }
                    throw IllegalArgumentException("Unable to construct a viewModel class.")
                }
            }
    }
}
