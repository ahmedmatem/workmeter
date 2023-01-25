package com.ahmedmatem.android.workmeter.ui.worksheet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentSealTabBinding
import com.ahmedmatem.android.workmeter.ui.login.afterTextChanged
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SealTabFragment : BaseFragment() {

    private lateinit var binding: FragmentSealTabBinding

    companion object {
        fun newInstance() = SealTabFragment()
    }

    override val viewModel: WorksheetViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSealTabBinding.inflate(inflater, container, false)
        binding.worksheet = viewModel.worksheetState.value

        binding.location.afterTextChanged {
            viewModel.locationChanged(it)
        }
        binding.width.afterTextChanged {
            viewModel.widthChanged(it)
        }
        binding.height.afterTextChanged {
            viewModel.heightChanged(it)
        }
        binding.camera.setOnClickListener {
            viewModel.navigationCommand.value = NavigationCommand.To(
                WorksheetFragmentDirections.actionWorksheetFragmentToCameraFragment()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.worksheetState.collect{
                    val worksheet = it ?: return@collect
                    binding.worksheet = worksheet
                }
            }
        }

        return binding.root
    }
}