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
import kotlinx.coroutines.flow.*
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
    private var _drawing: String? = null

    private val _drawings: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val drawings: StateFlow<List<String>> = _drawings.asStateFlow()

    private val _worksheetState: MutableStateFlow<Worksheet?> = MutableStateFlow(null)
    val worksheetState: StateFlow<Worksheet?> = _worksheetState

    private val _selectedDrawingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedDrawingIndex: StateFlow<Int> = _selectedDrawingIndex

    init {
        loadWorksheet()
        loadDrawings(siteId)
    }

    fun save(){
        viewModelScope.launch {
            _worksheetState.value =  _worksheetState.value!!
                .assign(_location, _width, _height, _worksheetState.value!!.photos, _drawing)
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

    fun drawingChanged(position: Int) {
        /**
         * Position 0 is reserved for Spinner default value, playing hint role in the spinner.
         * Real drawing's names start from position 1.
         */
        _drawing = if(position == 0) null else _drawings.value[position - 1]
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
                // Save drawing name in the cache
                _drawing = _worksheetState.value?.drawingUrl
            }
        }
    }

    fun getSelectedDrawingIndex() : Int {
        return _drawings.value.indexOf(_drawing) + 1
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
