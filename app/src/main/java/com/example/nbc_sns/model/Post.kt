package com.example.nbc_sns.model

data class Post(
    val postContent: String,
    val comments: List<Comment>,
)
