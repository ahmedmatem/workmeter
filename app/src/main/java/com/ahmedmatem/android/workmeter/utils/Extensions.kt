package com.ahmedmatem.android.workmeter.utils

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController

fun Fragment.onNavigateUp() {
    (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                android.R.id.home -> findNavController().navigateUp()
                else -> false
            }
        }

    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
}