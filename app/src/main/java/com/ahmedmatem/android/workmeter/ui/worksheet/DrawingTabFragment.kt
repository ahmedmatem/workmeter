package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentDrawingTabBinding

class DrawingTabFragment : BaseFragment() {

    private lateinit var binding: FragmentDrawingTabBinding

    companion object {
        fun newInstance() = DrawingTabFragment()
    }

    override val viewModel: WorksheetViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingTabBinding.inflate(inflater, container, false)

        return binding.root
    }
}