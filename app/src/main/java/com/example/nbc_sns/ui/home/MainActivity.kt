package com.example.nbc_sns.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbc_sns.R
import com.example.nbc_sns.model.PostImages
import com.example.nbc_sns.model.UserInfo
import com.example.nbc_sns.ui.PostManager
import com.example.nbc_sns.ui.UserManager
import com.example.nbc_sns.util.getUriToDrawable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createInitData()
    }

    private fun createInitData() {
        // 사용자 기본 정보 입력
        // TODO : 기본 사용자 4명, 각 사용자마다 게시물 5개 만들어주세요.
        val newJeans = UserInfo(
            id = "newjeans@gmail.com",
            pw = "defaultPassWord!"
            // TODO : 패스워드 규칙에 맞게 수정해주세요.
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
}