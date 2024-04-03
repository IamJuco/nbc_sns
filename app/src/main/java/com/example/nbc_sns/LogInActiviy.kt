package com.example.nbc_sns

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEng.setOnClickListener {
            var isEnglish = false
            isEnglish = !isEnglish

            val toastMessage = if (isEnglish) "Switched to English" else "한국어로 변경되었습니다"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()

            val putEmail = if (isEnglish) R.string.put_email else R.string.put_email1
            val putPassword = if (isEnglish) R.string.put_password else R.string.put_password1
            val login = if (isEnglish) R.string.login else R.string.login1
            val register = if (isEnglish) R.string.register else R.string.register1

            intent.getStringExtra("id") ?: ""
            intent.getStringExtra("pw") ?: ""

            binding.etEmail.hint = getString(putEmail)
            binding.etPw.hint = getString(putPassword)
            binding.btnLogin.text = getString(login)
            binding.btnRegister.text = getString(register)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pw = binding.etPw.text.toString()

            if (email.isNullOrBlank() || pw.isNullOrBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (pw.length < 8) {
                Toast.makeText(this, "비밀번호는 8자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fun register(userInfo: UserInfo): Boolean {
                val idExists = userInfo.id in UserManager.users
                val nicknameExists = userInfo.nickName in UserManager.users

                if (idExists && nicknameExists) {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    // 임시로 Main 대신 RegisterActivity 이동
                    val intent = Intent(this, RegisterActivity::class.java)
                    intent.putExtra("id", "")
                    intent.putExtra("pw", "")
                    startActivity(intent)
                    return true

                } else {
                    Toast.makeText(this, "등록되지 않은 사용자입니다", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}