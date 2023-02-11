package com.ahmedmatem.android.workmeter.ui.site

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.data.repository.SiteRepository
import com.ahmedmatem.android.workmeter.data.model.Site
import com.ahmedmatem.android.workmeter.data.repository.DrawingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SiteListViewModel(private val args: SiteListFragmentArgs) : BaseViewModel() {
    private val siteRepository: SiteRepository by inject(SiteRepository::class.java)
    private val drawingRepository: DrawingRepository by inject(DrawingRepository::class.java)

    private val _drawing: MutableStateFlow<ByteArray> = MutableStateFlow(byteArrayOf())
    val drawing: StateFlow<ByteArray> = _drawing

    val siteList : LiveData<List<Site>> = siteRepository.sites

    init {
        viewModelScope.launch {
            // refresh user sites
            siteRepository.refreshSites(args.loggedInUserArg)
            // refresh drawings
            drawingRepository.refreshDrawings(args.loggedInUserArg.displayName)
            // TODO: download drawings from the storage
            // TODO: CREATE GENERAL UPDATE STRATEGY
            try {
                drawingRepository.download(
                    "BXDdHXRI5ftx3Eunsi8R",
                    "drawing_1.png", "drawing_2.png"
                ).collect {
                    _drawing.value = it
                }
            } catch (e: Exception) {
                Log.d("DEBUG2", "${e.message}: ")
            }
        }
    }

    fun navigateToSite(site: Site){
        navigationCommand.value = NavigationCommand.To(
            SiteListFragmentDirections.actionSiteListFragmentToSiteFragment(
                site.name, site.id, args.loggedInUserArg)
        )
    }
}