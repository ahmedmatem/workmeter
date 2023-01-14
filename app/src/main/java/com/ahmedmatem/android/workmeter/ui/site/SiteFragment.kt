package com.ahmedmatem.android.workmeter.ui.site

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteBinding
import com.ahmedmatem.android.workmeter.utils.onNavigateUp

class SiteFragment : BaseFragment() {

    private lateinit var binding: FragmentSiteBinding
    override lateinit var viewModel: SiteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiteBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            viewModel.navigationCommand.value = NavigationCommand.To(
                SiteFragmentDirections.actionSiteFragmentToWorksheetFragment()
            )
        }

        viewModel = ViewModelProvider(this)[SiteViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**
         * Use onNavigateUp() extension function to handle navigation up
         * by activity MenuProvider
         * */
        onNavigateUp()
    }
}