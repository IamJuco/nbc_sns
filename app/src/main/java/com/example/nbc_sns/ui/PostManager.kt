package com.example.nbc_sns.ui

import com.example.nbc_sns.model.Comment
import com.example.nbc_sns.model.Post
import com.example.nbc_sns.model.PostImages

object PostManager {

    private val posts: HashMap<Int, Post> = hashMapOf() // key: postId, value: Post
    private var postId = 0

    fun addComment(postId: Int, newComment: Comment): Boolean {
        val post = posts[postId] ?: return false // postId에 해당하는 게시물이 없는 경우

        posts[postId] = post.copy(comments = post.comments + newComment)
        return true
    }

    // 등록된 포스트 객체를 반환함
    fun addPost(content: String, postImages: PostImages, userId: String): Post {
        val currentPostId = postId++
        val createdPost = Post(currentPostId, userId, content, postImages, emptyList())
        posts[currentPostId] = createdPost
        return createdPost
    }
}