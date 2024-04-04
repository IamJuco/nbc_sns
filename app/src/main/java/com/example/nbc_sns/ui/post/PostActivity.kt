package com.example.nbc_sns.ui.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.provider.MediaStore.Images.Thumbnails
import android.view.View
import com.example.nbc_sns.R
import com.example.nbc_sns.databinding.ActivityPostBinding
import com.example.nbc_sns.model.UserInfo
import com.example.nbc_sns.ui.detail.DetailPageActivity
import com.example.nbc_sns.ui.home.MainActivity

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // user id, post id 가져오기
        val userId = intent.getStringExtra(BUNDLE_KEY_FOR_USER_ID_CHECK) ?: ""
        val postId = intent.getStringExtra(BUNDLE_KEY_FOR_POST_ID_CHECK) ?: ""
        
        // Thumbnail 적용
//        binding.ivThumbnail.setImageResource()

        // NickName 적용
        binding.tvNickName.text = ""

        // PostImage 적용
//        binding.ivPostImage.setImageResource()

        // PostContents 적용
        binding.tvPostContent.text = ""

        // 뒤로가기 버튼 클릭 시 메인 화면으로 이동
        binding.btnPrev.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        // 썸네일 클릭 시 이미지 확대
        binding.ivThumbnail.setOnClickListener {
            binding.ivEnlargementThumbnail.visibility = View.VISIBLE
        }

        // 썸네일 확대 이미지 클릭 시 닫기
        binding.ivEnlargementThumbnail.setOnClickListener {
            binding.ivEnlargementThumbnail.visibility = View.GONE
        }

        // 닉네임 클릭 시 디테일 화면으로 이동
        binding.tvNickName.setOnClickListener {
            val intent = Intent(this, DetailPageActivity::class.java)
            startActivity(intent)
        }

        // 포스트이미지 클릭 시 이미지 확대
        binding.ivPostImage.setOnClickListener {
            binding.ivEnlargementPostImage.visibility = View.VISIBLE
        }

        // 포스트이미지 확대 이미지 클릭 시 닫기
        binding.ivEnlargementPostImage.setOnClickListener {
            binding.ivEnlargementPostImage.visibility = View.GONE
        }
    }

    companion object {
        const val BUNDLE_KEY_FOR_POST_ID_CHECK = "KEY_FOR_POST_ID_CHECK"
        const val BUNDLE_KEY_FOR_USER_ID_CHECK = "KEY_FOR_USER_ID_CHECK"
    }
}