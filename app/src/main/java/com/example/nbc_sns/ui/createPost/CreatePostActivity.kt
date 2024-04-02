package com.example.nbc_sns.ui.createPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbc_sns.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}