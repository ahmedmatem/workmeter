package com.ahmedmatem.android.workmeter.ui.sites

import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.site.SiteRepository
import org.koin.java.KoinJavaComponent.inject

class SiteListViewModel : BaseViewModel() {
    private val repo: SiteRepository by inject(SiteRepository::class.java)

    fun loadSites(uid: String){
        repo.loadSites(uid)
    }
}