package com.example.nbc_sns

import com.example.nbc_sns.model.UserInfo

object UserManager {

    val users: HashMap<String, UserInfo> = hashMapOf() // Key: UserId , value: UserInfo

    fun getUser(userId: String): UserInfo? {
        return users[userId]
    }

    fun register(userInfo: com.example.nbc_sns.model.UserInfo): Boolean {
        // 이미 존재하는 아이디, 닉네임과 겹치는지 검증
        if (userInfo.id in users && userInfo.nickName in users) {
            return false // 가입 처리가 안 된 것
        }
        users[userInfo.id] != null
        users[userInfo.nickName] != null
        return true
    }

    // 존재하지 않는 회원에 대해 회원 탈퇴를 시도할 경우 false, 아니면  true를 반환함
    fun unregister(userId: String): Boolean {
        return users.remove(userId) != null
    }
}