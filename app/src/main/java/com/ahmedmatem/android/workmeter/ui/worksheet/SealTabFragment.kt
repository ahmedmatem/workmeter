package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSealTabBinding

class SealTabFragment : BaseFragment() {

    private lateinit var binding: FragmentSealTabBinding

    companion object {
        fun newInstance() = SealTabFragment()
    }

    override val viewModel: WorksheetViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSealTabBinding.inflate(inflater, container, false)
        binding.worksheet = viewModel.worksheet

        return binding.root
    }
}