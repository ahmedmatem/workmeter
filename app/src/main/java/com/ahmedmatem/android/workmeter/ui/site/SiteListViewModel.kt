package com.ahmedmatem.android.workmeter.ui.site

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.repository.DrawingRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SiteListViewModel(private val args: SiteListFragmentArgs) : BaseViewModel() {
    private val siteRepository: SiteRepository by inject(SiteRepository::class.java)
    private val drawingRepository: DrawingRepository by inject(DrawingRepository::class.java)

    val siteList : LiveData<List<Site>> = siteRepository.sites

    init {
        viewModelScope.launch {
            // refresh user sites
            siteRepository.refreshSites(args.loggedInUserArg)
            drawingRepository.refreshDrawings(args.loggedInUserArg.displayName)
        }
    }

    fun navigateToSite(site: Site){
        navigationCommand.value = NavigationCommand.To(
            SiteListFragmentDirections.actionSiteListFragmentToSiteFragment(
                site.name, site.id, args.loggedInUserArg)
        )
    }
}