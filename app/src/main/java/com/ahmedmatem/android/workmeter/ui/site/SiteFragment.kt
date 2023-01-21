package com.ahmedmatem.android.workmeter.ui.site

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteBinding

class SiteFragment : BaseFragment() {

    private val args: SiteFragmentArgs by navArgs()
    private lateinit var binding: FragmentSiteBinding

    override val viewModel: SiteViewModel by viewModels { SiteViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiteBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            viewModel.navigateToWorksheet(args.siteId)
        }

        return binding.root
    }
}