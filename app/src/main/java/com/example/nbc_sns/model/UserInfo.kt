package com.example.nbc_sns.model

import android.graphics.Bitmap

data class UserInfo(
    val id: String,
    val nickName: String,
    val thumbnail: Bitmap,
    val introduction: String,
    val posts: List<Post>,
)
