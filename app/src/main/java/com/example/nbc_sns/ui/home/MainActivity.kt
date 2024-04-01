package com.example.nbc_sns.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.example.nbc_sns.R
import com.example.nbc_sns.model.Post
import com.example.nbc_sns.model.UserInfo
import com.example.nbc_sns.ui.PostManager
import com.example.nbc_sns.ui.UserManager

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
            pw = "defaultPassWord!", // TODO : 패스워드 규칙에 맞게 수정해주세요.
            nickName = "newjeans_official",
            thumbnail = AppCompatResources.getDrawable(
                baseContext,
                R.drawable.new_jeans_thumbnail
            )!!.toBitmap(),
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
            listOf(
                AppCompatResources.getDrawable(
                    baseContext,
                    R.drawable.new_jeans_sample_post_image
                )!!.toBitmap()
            ),
            newJeans.id,
        )
    }
}