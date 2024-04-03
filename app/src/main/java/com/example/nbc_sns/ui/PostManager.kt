package com.example.nbc_sns.ui

import com.example.nbc_sns.model.Post
import com.example.nbc_sns.model.PostImages

object PostManager {

    private val posts: HashMap<String, Set<Post>> = hashMapOf() // key: userId, value: Set<Post>
    private var postId = 0

    // 등록된 포스트 객체를 반환함
    fun addPost(content: String, postImages: PostImages, userId: String): Post {
        val currentPostId = postId++
        val createdPost = Post(currentPostId, userId, content, postImages, emptyList())
        posts[userId] = posts.getOrDefault(userId, setOf()) + createdPost
        return createdPost
    }

    fun getAllPosts(): List<Post> {
        return posts.values.fold(listOf()) { acc, posts ->
            acc + posts
        }
    }

    fun getPost(userId: String): List<Post> {
        return posts[userId]?.toList() ?: emptyList()
    }
}