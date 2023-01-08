package com.ahmedmatem.android.workmeter.ui.sites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser

class SiteListFragment : BaseFragment() {

    override lateinit var viewModel: SiteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SiteListViewModel::class.java]

        viewModel.loadSites(
            LoggedInUser("fTHD4K3jCJhmAVyoK1UiNoRMDe32", "ahmedmatem@gmail.com")
        )

        return inflater.inflate(R.layout.fragment_site_list, container, false)
    }

}