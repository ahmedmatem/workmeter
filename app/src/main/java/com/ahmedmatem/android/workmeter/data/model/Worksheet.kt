package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Worksheet(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "site_id") val siteId: String,
    val date: String,
    // TODO: write logic for sealNum generation
    @ColumnInfo(name = "seal_num") val sealNum: Int,
    val location: String,
    val width: String,
    val height: String,
    @ColumnInfo(name = "drawing_url") val drawingUrl: String? = null,
    val state: State,
    // TODO: Implement photos property
    // ...
) {
    companion object {
        /**
         * Companion function sets default value to newly created worksheet.
         *
         *  Arguments:
         * * siteId - the id of Site which worksheet is created for
         * * sealNum - the number of seal generated automatically equals
         *             the number of worksheets already  plus 1.
         */
        fun default(siteId: String, sealNum: Int) : Worksheet {
            return Worksheet(
                id = UUID.randomUUID().toString(),
                siteId = siteId,
                date = SimpleDateFormat("dd-MM-yyyy EEE").format(Calendar.getInstance().time),
                sealNum = sealNum,
                location = "",
                width = "",
                height = "",
                state = State.STARTED
            )
        }
    }
}

fun Worksheet.assign(location: String, width: String, height: String, state: State? = null) : Worksheet {
    return Worksheet(
        id = id,
        siteId = siteId,
        date = date,
        sealNum = sealNum,
        location = location,
        width = width,
        height = height,
        state = state ?: this.state
    )
}

enum class State {
    STARTED, INCOMPLETE, DONE
}

