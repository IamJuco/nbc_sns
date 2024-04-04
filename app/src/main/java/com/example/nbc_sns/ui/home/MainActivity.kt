package com.example.nbc_sns.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.databinding.ActivityMainBinding
import com.example.nbc_sns.model.PostImages
import com.example.nbc_sns.model.UserInfo
import com.example.nbc_sns.ui.PostManager
import com.example.nbc_sns.ui.UserManager
import com.example.nbc_sns.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fakeSetting()
        val posts = PostManager.getAllPosts().toMutableList() // Post가 리스트형태라서 mutableList로
        UserManager.getUser(posts.first().authorId)?.thumbnail

        binding.rvPostArea.adapter = PostListItemAdapter(posts)

        binding.rvProfileArea.adapter = ProfileItemAdapter(posts)

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
                    Uri.parse("android.resource://com.example.nbc_sns/drawable/new_jeans_sample_post_image"),
                )
            ),
            newJeans.id,
        )
    }
}