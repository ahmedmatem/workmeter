package com.ahmedmatem.android.workmeter.ui.site

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentSiteListBinding
import com.ahmedmatem.android.workmeter.utils.saveBitmapInGallery
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SiteListFragment : BaseFragment() {

    private val args: SiteListFragmentArgs by navArgs()
    override lateinit var viewModel: SiteListViewModel
    private lateinit var binding: FragmentSiteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSiteListBinding.inflate(inflater, container, false)
        val adapter = SiteListAdapter(SiteListAdapter.OnClickListener{
            viewModel.navigateToSite(it)
        })
        binding.siteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.siteRecycler.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            SiteListViewModelFactory(args)
        )[SiteListViewModel::class.java]

        viewModel.siteList.observe(viewLifecycleOwner, Observer {
            val siteList = it ?: return@Observer
            adapter.submitList(siteList)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.drawing.collect { listOfByteArray ->
                    listOfByteArray.forEach { byteArray ->
                        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        saveBitmapInGallery(
                            bitmap,
                            "yyyy-MM-dd-HH-mm-ss-SSS",
                            "Pictures/Workmeter-Image"
                        )
                    }
                }
            }
        }

        return binding.root
    }
}