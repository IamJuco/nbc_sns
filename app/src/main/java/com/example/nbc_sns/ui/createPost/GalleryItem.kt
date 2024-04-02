package com.example.nbc_sns.ui.createPost

import android.net.Uri
import java.util.Date

data class GalleryItem(
    val id: Long,
    val uri: Uri,
    val date: Date,
)
