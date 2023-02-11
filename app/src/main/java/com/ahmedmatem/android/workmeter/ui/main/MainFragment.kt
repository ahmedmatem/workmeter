package com.ahmedmatem.android.workmeter.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentMainBinding
import com.ahmedmatem.android.workmeter.utils.clearFullScreen
import com.ahmedmatem.android.workmeter.utils.setFullScreen

class MainFragment : BaseFragment() {

    private lateinit var _binding: FragmentMainBinding
    private val args: MainFragmentArgs by navArgs()

    override val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        setFullScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        _binding.scheduleWorkButton.setOnClickListener {
            viewModel.navigationCommand.value = NavigationCommand.To(
                MainFragmentDirections.actionMainFragmentToSiteListFragment(args.loggedInUserArg)
            )
        }

        return _binding.root
    }

    override fun onStop() {
        Log.d("DEBUG2", "onDestroy: ")
        clearFullScreen()
        super.onStop()
    }
}