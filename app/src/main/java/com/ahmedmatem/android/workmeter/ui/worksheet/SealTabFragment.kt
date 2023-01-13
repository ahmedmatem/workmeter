package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSealTabBinding

class SealTabFragment : BaseFragment() {

    private lateinit var binding: FragmentSealTabBinding

    companion object {
        fun newInstance() = SealTabFragment()
    }

    override lateinit var viewModel: SealTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSealTabBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SealTabViewModel::class.java]

        binding.date.text = viewModel.now

        return binding.root
    }
}