package com.ahmedmatem.android.workmeter.ui.sites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.data.model.LoggedInUser

class SiteListFragment : BaseFragment() {

    companion object {
        fun newInstance() = SiteListFragment()
    }

    override lateinit var viewModel: SiteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SiteListViewModel::class.java)

        viewModel.loadSites(
            LoggedInUser("fTHD4K3jCJhmAVyoK1UiNoRMDe32", "ahmedmatem@gmail.com")
        )

        return inflater.inflate(R.layout.fragment_site_list, container, false)
    }

}