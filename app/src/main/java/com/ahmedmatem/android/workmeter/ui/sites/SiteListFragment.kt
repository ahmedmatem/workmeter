package com.ahmedmatem.android.workmeter.ui.sites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment

class SiteListFragment : BaseFragment() {

    private val args: SiteListFragmentArgs by navArgs()
    override lateinit var viewModel: SiteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            this,
            SiteListViewModelFactory(args)
        )[SiteListViewModel::class.java]

        // TODO: Implement RecyclerView for site list

        viewModel.siteList.observe(viewLifecycleOwner, Observer {
            val siteList = it ?: return@Observer
            // TODO: Update UI with site list...
            viewModel.showToast.value = "Site list was updated"
        })

        return inflater.inflate(R.layout.fragment_site_list, container, false)
    }

}