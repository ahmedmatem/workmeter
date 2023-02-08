package com.ahmedmatem.android.workmeter.ui.site

import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository
import org.koin.java.KoinJavaComponent.inject

class SiteViewModel(private val siteId: String) : BaseViewModel() {
    private val worksheetRepository: WorksheetRepository by inject(WorksheetRepository::class.java)

    val incompleteWorksheets = worksheetRepository.getAllIncompleteWorksheets(siteId)

    fun navigateToWorksheet(loggedInUser: LoggedInUser, worksheetId: String? = null) {
        navigationCommand.value = NavigationCommand.To(
            SiteFragmentDirections.actionSiteFragmentToWorksheetFragment(
                siteId, loggedInUser, worksheetId)
        )
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            @Suppress("UNCHECKED_CAST") object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val siteId: String = extras[DEFAULT_ARGS_KEY]?.getString("siteId")!!
                if(modelClass.isAssignableFrom(SiteViewModel::class.java)) {
                    return SiteViewModel(siteId) as T
                }
                throw IllegalArgumentException("Unable to construct a viewModel.")
            }
        }
    }
}