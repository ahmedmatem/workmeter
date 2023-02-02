package com.ahmedmatem.android.workmeter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahmedmatem.android.workmeter.ui.worksheet.PhotoData
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Worksheet(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "site_id") val siteId: String,
    val date: String,
    @ColumnInfo(name = "seal_num") val sealNum: Int,
    val location: String,
    val width: String,
    val height: String,
    @ColumnInfo(name = "drawing_url") val drawingUrl: String? = null,
    val isComplete: Boolean,
    val photos: String, // 'uri_1,uri_2,...uri_last,'
) {
    companion object {
        const val COMMA_DELIMITER = ','
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
                date = SimpleDateFormat("dd-MM-yyyy EEE", Locale.UK).format(Calendar.getInstance().time),
                sealNum = sealNum,
                location = "",
                width = "",
                height = "",
                isComplete = false,
                photos = ""
            )
        }
    }
}

/**
 * Helper function converts photos from String to List
 */
fun Worksheet.photosToList() : List<PhotoData> =
    listOf(*(photos.split(Worksheet.COMMA_DELIMITER).toTypedArray())).map { uri ->
        PhotoData(id, uri)
    }

fun Worksheet.assign(
    location: String,
    width: String,
    height: String,
    photos: String,
    isComplete: Boolean = false) : Worksheet {
    return Worksheet(
        id = id,
        siteId = siteId,
        date = date,
        sealNum = sealNum,
        location = location,
        width = width,
        height = height,
        photos = photos,
        isComplete = isComplete
    )
}

