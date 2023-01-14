package com.ahmedmatem.android.workmeter.ui.site

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteListBinding
import com.ahmedmatem.android.workmeter.ui.MainActivity

class SiteListFragment : BaseFragment() {

    private val args: SiteListFragmentArgs by navArgs()
    override lateinit var viewModel: SiteListViewModel
    private lateinit var binding: FragmentSiteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiteListBinding.inflate(inflater, container, false)
        val adapter = SiteListAdapter(SiteListAdapter.OnClickListener{
            viewModel.navigateToSite(it)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.siteListFragment))
        NavigationUI.setupActionBarWithNavController(
            activity as AppCompatActivity,
            navController,
            appBarConfiguration
        )
    }
}