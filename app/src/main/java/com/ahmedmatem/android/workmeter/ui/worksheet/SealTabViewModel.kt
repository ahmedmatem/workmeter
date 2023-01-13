package com.ahmedmatem.android.workmeter.ui.worksheet

import com.ahmedmatem.android.workmeter.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class SealTabViewModel : BaseViewModel() {
    val now = SimpleDateFormat("dd-MM-yyyy EEE").format(Calendar.getInstance().time)
}