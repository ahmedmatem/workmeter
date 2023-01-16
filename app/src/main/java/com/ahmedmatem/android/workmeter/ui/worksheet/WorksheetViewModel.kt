package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Size
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.model.Worksheet
import com.ahmedmatem.android.workmeter.data.repository.WorksheetRepository

// TODO: create worksheet viewModel factory
class WorksheetViewModel(private val repository: WorksheetRepository) : BaseViewModel() {
    // TODO: read worksheet from local database use StateFlow
    val worksheet: Worksheet = Worksheet(id = "123", siteId = "1231313", sealNum = 4, drawingUrl = "", location = "", size = Size(-6, -3).toString())

    // TODO: write a function that save worksheet in local database
}