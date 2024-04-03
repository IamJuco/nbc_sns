package com.example.nbc_sns.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.nbc_sns.ui.editProfile.EditProfileActivity.Companion.BUNDLE_KEY_FOR_USER_ID_CHECK
import com.example.nbc_sns.ui.home.MainActivity
import com.example.nbc_sns.ui.post.PostActivity
import com.example.nbc_sns.ui.post.PostActivity.Companion.BUNDLE_KEY_FOR_POST_ID_CHECK
import com.example.nbc_sns.ui.selectImage.SelectImageActivity
import com.example.nbc_sns.util.getUriToDrawable

class ProfileActivity : AppCompatActivity(), PostClickListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userId: String
    private val adapter by lazy {
        ProfileAdapter(this)
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            updateUserProfileImage(uri)
        }
    }

    private fun updateUserProfileImage(uri: Uri) {
        UserManager.updateUserInfo(userId, uri)
        updateUserProfile()
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

        fakeSetting()
        setAdapter()
        initView()
        setListener()
    }

    private fun fakeSetting() {
        // 사용자 기본 정보 입력
        // TODO : 기본 사용자 4명, 각 사용자마다 게시물 5개 만들어주세요.
        val newJeans = UserInfo(
            id = "newjeans@gmail.com",
            pw = "defaultPassWord!", // TODO : 패스워드 규칙에 맞게 수정해주세요.
            nickName = "newjeans_official",
            thumbnail = Uri.parse("android.resource://com.example.nbc_sns/drawable/new_jeans_thumbnail"),
            introduction = "최고의 토론만을 펼치는 엉망잔칭 토론클럽입니다.\n" +
                    "매년 엉망잔칭 토론회를 주최하고 있습니다.\n"
        )
        UserManager.register(newJeans)
        // 사용자 기본 게시물 입력
        PostManager.addPost(
            "[엉망잔칭 토론클럽]\n" +
                    "\n" +
                    "닭이 먼저다 vs 달걀이 먼저다\n" +
                    "여러분의 의견은 어떠신가요?\n" +
                    "\n" +
                    "#엉망잔칭토론클럽 #엉망잔칭토론회",
            PostImages(
                listOf(
                    getUriToDrawable(baseContext, R.drawable.new_jeans_sample_post_image)
                )
            ),
            newJeans.id,
        )
    }

    private fun setAdapter() {
        binding.rvPost.adapter = adapter
    }

    private fun initView() {
        // TODO : 로그인 여부에 따라 프로필 수정, 게시글 작성, 로그아웃 버튼 visibility 수정 필요

        binding.ivThumbnail.clipToOutline = true // xml 설정은 API 30 이하에서 적용되지 않아 코드로 적용해야 함
        userId = "newjeans@gmail.com" // TODO : 다른 화면에서 Intent로 전달한 userId를 이용하도록 수정해야 함
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
        binding.btnCreatePost.setOnClickListener {
            startActivity(Intent(this, SelectImageActivity::class.java).apply {
                putExtra(BUNDLE_KEY_FOR_USER_ID, userId)
            })
        }
        binding.btnEditProfile.setOnClickListener {
            val mimeType = "image/*"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
        }
        binding.btnLogout.setOnClickListener {
            // TODO : 로그아웃 처리 필요
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
    }

    companion object {
        const val BUNDLE_KEY_FOR_CREATE_POST_CHECK = "KEY_FOR_CREATE_POST_CHECK"
    }
}