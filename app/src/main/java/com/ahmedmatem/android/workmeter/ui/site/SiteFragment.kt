package com.ahmedmatem.android.workmeter.ui.site

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteBinding

class SiteFragment : BaseFragment() {

    private val args: SiteFragmentArgs by navArgs()
    private lateinit var binding: FragmentSiteBinding

    override val viewModel: SiteViewModel by viewModels { SiteViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiteBinding.inflate(inflater, container, false)

        // TODO: Show summary for complete worksheets

        // TODO: Show list of incomplete worksheets

        binding.fab.setOnClickListener {
            viewModel.navigateToWorksheet(args.siteId)
        }

        return binding.root
    }
}