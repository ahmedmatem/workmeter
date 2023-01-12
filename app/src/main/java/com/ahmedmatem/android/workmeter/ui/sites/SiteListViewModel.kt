package com.ahmedmatem.android.workmeter.ui.sites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.Result
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser
import com.ahmedmatem.android.workmeter.data.site.SiteRepository
import com.ahmedmatem.android.workmeter.data.site.local.Site
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SiteListViewModel(private val args: SiteListFragmentArgs) : BaseViewModel() {
    private val siteRepository: SiteRepository by inject(SiteRepository::class.java)

    val siteList : LiveData<List<Site>> = siteRepository.sites

    init {
        viewModelScope.launch {
            siteRepository.refreshSites(args.loggedInUserArg)
        }
    }

    fun navigateToSite(site: Site){
        navigationCommand.value = NavigationCommand.To(
            SiteListFragmentDirections.actionSiteListFragmentToSiteFragment()
        )
    }
}