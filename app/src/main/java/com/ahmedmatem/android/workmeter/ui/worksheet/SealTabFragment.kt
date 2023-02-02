package com.ahmedmatem.android.workmeter.ui.worksheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.data.model.photosToList
import com.ahmedmatem.android.workmeter.databinding.FragmentSealTabBinding
import com.ahmedmatem.android.workmeter.ui.login.afterTextChanged
import kotlinx.coroutines.launch

class SealTabFragment : BaseFragment() {

    private lateinit var binding: FragmentSealTabBinding

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
            viewModel.navigateToCamera()
        }

        /**
         * Photo recycler UI
         */
        val adapter = PhotoListAdapter(PhotoListAdapter.OnClickListener {
            // TODO: Implement onPhoto click listener
            viewModel.showToast.value = "Photo was clicked."
        })
        binding.photosRecyclerView.layoutManager =
            StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        binding.photosRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.worksheetState.collect{
                    val worksheet = it ?: return@collect
                    binding.worksheet = worksheet
                    adapter.submitList(worksheet.photosToList())
                }
            }
        }

        return binding.root
    }

    companion object {
        const val SPAN_COUNT = 3
        fun newInstance() = SealTabFragment()
    }
}