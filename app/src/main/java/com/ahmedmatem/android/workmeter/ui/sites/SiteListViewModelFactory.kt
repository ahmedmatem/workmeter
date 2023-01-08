package com.ahmedmatem.android.workmeter.ui.sites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SiteListViewModelFactory(private val args: SiteListFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SiteListViewModel::class.java))
            return SiteListViewModel(args) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}