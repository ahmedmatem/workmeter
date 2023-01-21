package com.ahmedmatem.android.workmeter.ui.site

import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand

class SiteViewModel(private val siteId: String) : BaseViewModel() {

    init {}

    fun navigateToWorksheet(siteId: String) {
        navigationCommand.value = NavigationCommand.To(
            SiteFragmentDirections.actionSiteFragmentToWorksheetFragment(siteId)
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