package com.ahmedmatem.android.workmeter.ui.worksheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmedmatem.android.workmeter.R
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.databinding.FragmentWorksheetBinding
import com.google.android.material.tabs.TabLayoutMediator

class WorksheetFragment : BaseFragment() {

    private lateinit var binding: FragmentWorksheetBinding
    private lateinit var tabCollectionAdapter: TabCollectionAdapter

    override val viewModel: WorksheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorksheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabCollectionAdapter = TabCollectionAdapter(this)
        binding.viewPager.adapter = tabCollectionAdapter
        // link tab_layout with view_pager and attach it
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            when(position){
                0 -> tab.text = resources.getString(R.string.text_seal)
                1 -> tab.text = resources.getString(R.string.text_drawing)
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }.attach()
    }

    class TabCollectionAdapter(fragment: BaseFragment): FragmentStateAdapter(fragment){
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): BaseFragment {
            return when(position){
                0 -> SealTabFragment.newInstance()
                1 -> DrawingTabFragment.newInstance()
                else -> throw IllegalArgumentException( "Invalid position: position(${position})"                )
            }
        }
    }
}