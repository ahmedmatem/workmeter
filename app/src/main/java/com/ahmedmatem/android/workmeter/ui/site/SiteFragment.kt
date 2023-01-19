package com.ahmedmatem.android.workmeter.ui.site

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteBinding

class SiteFragment : BaseFragment() {

    private val args: SiteFragmentArgs by navArgs()
    private lateinit var binding: FragmentSiteBinding
    override lateinit var viewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiteBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            viewModel.navigateToWorksheet(args.siteId)
        }

        viewModel = ViewModelProvider(this)[SiteViewModel::class.java]

        return binding.root
    }
}