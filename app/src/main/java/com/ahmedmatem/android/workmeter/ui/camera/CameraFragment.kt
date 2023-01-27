package com.ahmedmatem.android.workmeter.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment

class CameraFragment : BaseFragment() {
    override val viewModel: CameraViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }


}