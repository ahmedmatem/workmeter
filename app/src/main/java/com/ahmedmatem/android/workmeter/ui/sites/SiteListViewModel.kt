package com.ahmedmatem.android.workmeter.ui.sites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmedmatem.android.workmeter.base.BaseViewModel
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

//    fun loadSites(user: LoggedInUser){
//        viewModelScope.launch {
//            siteRepository.loadSites(user)
//                .catch { exception ->
//                    Log.d("Firestore", "loadSites error: ${exception.message}")
//                    // notifyError(exception)
//                }
//                .collect { result ->
//                    if(result is Result.Success){
//                        Log.d("Firestore", "loadSites: ${result.data}")
//                    }
//                    if(result is Result.Error){
//                        Log.d("Firestore", "loadSites error: ${result.exception.message}")
//                    }
//                }
//        }
//    }
}