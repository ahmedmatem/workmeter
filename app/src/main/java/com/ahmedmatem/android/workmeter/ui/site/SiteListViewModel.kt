package com.ahmedmatem.android.workmeter.ui.site

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.model.Site
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
            SiteListFragmentDirections.actionSiteListFragmentToSiteFragment(site.name, site.id)
        )
    }
}