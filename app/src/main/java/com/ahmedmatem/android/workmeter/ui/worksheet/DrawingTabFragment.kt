package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentDrawingTabBinding

class DrawingTabFragment : BaseFragment() {

    private lateinit var binding: FragmentDrawingTabBinding

    companion object {
        fun newInstance() = DrawingTabFragment()
    }

    override lateinit var viewModel: DrawingTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingTabBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DrawingTabViewModel::class.java]

        return binding.root
    }
}