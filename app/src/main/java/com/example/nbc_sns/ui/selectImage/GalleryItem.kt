package com.example.nbc_sns.ui.selectImage

import android.net.Uri
import java.util.Date

data class GalleryItem(
    val id: Long,
    val uri: Uri,
    val date: Date,
)
