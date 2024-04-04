package com.example.nbc_sns.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.databinding.ActivityMainBinding
import com.example.nbc_sns.ui.PostManager
import com.example.nbc_sns.ui.UserManager
import com.example.nbc_sns.ui.profile.ProfileActivity
import com.example.nbc_sns.util.insertDummyData

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertDummyData()
        val posts = PostManager.getAllPosts().toMutableList() // Post가 리스트형태라서 mutableList로
        UserManager.getUser(posts.first().authorId)?.thumbnail

        binding.rvPostArea.adapter = PostListItemAdapter(posts)

        val allUsers = UserManager.getAllUser().toMutableList()
        binding.rvProfileArea.adapter = ProfileItemAdapter(allUsers)

        onClickMyPage()
    }

    private fun onClickMyPage() {
        binding.ivMyPage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            val userId = UserManager.loggedId
            intent.putExtra(ProfileActivity.BUNDLE_KEY_FOR_USER_ID_CHECK, userId)
            startActivity(intent)
        }
    }
}