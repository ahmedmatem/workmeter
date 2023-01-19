package com.ahmedmatem.android.workmeter.ui.site

import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand

class SiteViewModel : BaseViewModel() {

    fun navigateToWorksheet(siteId: String) {
        navigationCommand.value = NavigationCommand.To(
            SiteFragmentDirections.actionSiteFragmentToWorksheetFragment(siteId)
        )
    }
}