package com.ahmedmatem.android.workmeter.ui.worksheet

import android.util.Size
import com.ahmedmatem.android.workmeter.base.BaseViewModel
import com.ahmedmatem.android.workmeter.data.model.Worksheet

class WorksheetViewModel : BaseViewModel() {
    val worksheet: Worksheet = Worksheet(id = "123", sealNum = 4, drawingUrl = "", location = "", size = Size(-6, -3).toString())
}