package com.ahmedmatem.android.workmeter.ui.workrecord

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentWorkRecordBinding

class WorkRecordFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkRecordBinding
    override lateinit var viewModel: WorkRecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkRecordBinding.inflate(inflater, container, false)


        return binding.root
    }
}