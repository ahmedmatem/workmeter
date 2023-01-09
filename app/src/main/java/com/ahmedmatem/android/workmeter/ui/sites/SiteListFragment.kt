package com.ahmedmatem.android.workmeter.ui.sites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteListBinding

class SiteListFragment : BaseFragment() {

    private val args: SiteListFragmentArgs by navArgs()
    override lateinit var viewModel: SiteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSiteListBinding.inflate(inflater, container, false)
        val adapter = SiteListAdapter(SiteListAdapter.OnClickListener{
            // TODO: do something with site data
        })
        binding.siteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.siteRecycler.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            SiteListViewModelFactory(args)
        )[SiteListViewModel::class.java]

        viewModel.siteList.observe(viewLifecycleOwner, Observer {
            val siteList = it ?: return@Observer

            adapter.submitList(siteList)
        })

        return binding.root
    }

}