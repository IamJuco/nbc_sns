package com.example.nbc_sns.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.R
import com.example.nbc_sns.databinding.ActivityProfileBinding
import com.example.nbc_sns.model.PostImages
import com.example.nbc_sns.model.UserInfo
import com.example.nbc_sns.ui.PostManager
import com.example.nbc_sns.ui.UserManager
import com.example.nbc_sns.ui.createPost.CreatePostActivity.Companion.BUNDLE_KEY_FOR_USER_ID
import com.example.nbc_sns.ui.home.MainActivity
import com.example.nbc_sns.ui.post.PostActivity
import com.example.nbc_sns.ui.post.PostActivity.Companion.BUNDLE_KEY_FOR_POST_ID_CHECK
import com.example.nbc_sns.ui.post.PostActivity.Companion.BUNDLE_KEY_FOR_USER_ID_CHECK
import com.example.nbc_sns.ui.selectImage.SelectImageActivity
import com.example.nbc_sns.util.getUriToDrawable

class ProfileActivity : AppCompatActivity(), PostClickListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userId: String
    private var isEditIntroduction = false
    private val adapter by lazy {
        ProfileAdapter(this)
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                updateUserProfileImage(uri)
            }
        }

    private fun updateUserProfileImage(uri: Uri) {
        val isUpdateDone = UserManager.updateUserThumbnail(userId, uri)
        if (isUpdateDone) {
            updateUserProfile()
        } else {
            Toast.makeText(this, getString(R.string.fail_edit_profile), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onClick(postId: Int) {
        startActivity(Intent(this, PostActivity::class.java).apply {
            putExtra(BUNDLE_KEY_FOR_POST_ID_CHECK, postId)
            putExtra(BUNDLE_KEY_FOR_USER_ID_CHECK, userId)
        })
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent?.getBooleanExtra(BUNDLE_KEY_FOR_CREATE_POST_CHECK, false) == true) {
            updateUIAboutPost()
            return
        }
        super.onNewIntent(intent)
    }

    private fun updateUIAboutPost() {
        val posts = PostManager.getPost(userId)
        binding.tvPostCount.text = posts.count().toString()
        adapter.submitList(posts)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        initView()
        setListener()
    }

    private fun setAdapter() {
        binding.rvPost.adapter = adapter
    }

    private fun initView() {
        // TODO : 로그인 여부에 따라 프로필 수정, 게시글 작성, 로그아웃 버튼 visibility 수정 필요

        binding.ivThumbnail.clipToOutline = true // xml 설정은 API 30 이하에서 적용되지 않아 코드로 적용해야 함
        userId = intent.getStringExtra(BUNDLE_KEY_FOR_USER_ID_CHECK) ?: run {
            Toast.makeText(this, "유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        updateUserProfile()
        updateUIAboutPost()
    }

    private fun updateUserProfile() {
        val userInfo = UserManager.getUser(userId) ?: run {
            Toast.makeText(this, "유저 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        binding.ivThumbnail.setImageURI(userInfo.thumbnail)
        binding.tvNickname.text = userInfo.nickName
        binding.tvIntroduction.text = userInfo.introduction
    }

    private fun setListener() {
        binding.ibBack.setOnClickListener {
            finish()
        }
        binding.btnCreatePost.setOnClickListener {
            startActivity(Intent(this, SelectImageActivity::class.java).apply {
                putExtra(BUNDLE_KEY_FOR_USER_ID, userId)
            })
        }
        binding.btnEditProfileThumbnail.setOnClickListener {
            val mimeType = "image/*"
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.SingleMimeType(
                        mimeType
                    )
                )
            )
        }
        binding.btnEditProfileIntroduction.setOnClickListener {
            if (isEditIntroduction) {
                binding.btnEditProfileIntroduction.text =
                    getString(R.string.edit_profile_introduction)
                val editedText = binding.edtIntroduction.text.toString()
                binding.edtIntroduction.visibility = View.INVISIBLE
                binding.tvIntroduction.visibility = View.VISIBLE
                updateUserProfileInstruction(editedText)
            } else {
                binding.btnEditProfileIntroduction.text =
                    getString(R.string.edit_profile_introduction_done)
                binding.edtIntroduction.setText(binding.tvIntroduction.text)
                binding.edtIntroduction.visibility = View.VISIBLE
                binding.tvIntroduction.visibility = View.INVISIBLE
            }
            isEditIntroduction = !isEditIntroduction
        }
        binding.btnLogout.setOnClickListener {
            // TODO : 로그아웃 처리 필요
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
    }

    private fun updateUserProfileInstruction(introduction: String) {
        val isUpdateDone = UserManager.updateUserIntroduction(userId, introduction)
        if (isUpdateDone) {
            updateUserProfile()
        } else {
            Toast.makeText(this, getString(R.string.fail_edit_profile), Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val BUNDLE_KEY_FOR_USER_ID_CHECK = "KEY_FOR_USER_ID_CHECK"
        const val BUNDLE_KEY_FOR_CREATE_POST_CHECK = "KEY_FOR_CREATE_POST_CHECK"
    }
}