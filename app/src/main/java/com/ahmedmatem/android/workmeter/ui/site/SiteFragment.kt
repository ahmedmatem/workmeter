package com.ahmedmatem.android.workmeter.ui.site

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteBinding
import com.ahmedmatem.android.workmeter.ui.worksheet.WorksheetListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SiteFragment : BaseFragment() {

    private val args: SiteFragmentArgs by navArgs()
    private lateinit var binding: FragmentSiteBinding

    private lateinit var adapter: WorksheetListAdapter

    override val viewModel: SiteViewModel by viewModels { SiteViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiteBinding.inflate(inflater, container, false)
        adapter = WorksheetListAdapter(WorksheetListAdapter.OnClickListener {
            // TODO: Implement INCOMPLETE Worksheet click Event

        })

        // TODO: Show summary for complete worksheets

        // TODO: Show list of incomplete worksheets
        binding.worksheetRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.worksheetRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.incompleteWorksheets.collect{
                    Log.d("DEBUG", "onCreateView: $it")
                    adapter.submitList(it)
                }
            }
        }

        binding.fab.setOnClickListener {
            viewModel.navigateToWorksheet(args.siteId)
        }

        return binding.root
    }
}