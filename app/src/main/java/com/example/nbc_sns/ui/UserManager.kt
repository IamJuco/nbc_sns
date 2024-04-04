package com.example.nbc_sns.ui

import android.net.Uri
import com.example.nbc_sns.model.PostImages
import com.example.nbc_sns.model.UserInfo

object UserManager {

    init {
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
        register(newJeans)
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

    private val users: HashMap<String, UserInfo> = hashMapOf() // Key: UserId , value: UserInfo

    fun getUser(userId: String): UserInfo? {
        return users[userId]
    }

    fun register(userInfo: UserInfo): Boolean {
        // 이미 존재하는 아이디와 겹치는지 검증
        if (userInfo.id in users) {
            return false // 가입 처리가 안 된 것
        }
        users[userInfo.id] = userInfo
        return true
    }

    // 존재하지 않는 회원에 대해 회원 탈퇴를 시도할 경우 false, 아니면  true를 반환함
    fun unregister(userId: String): Boolean {
        return users.remove(userId) != null
    }

}