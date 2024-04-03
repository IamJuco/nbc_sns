package com.example.nbc_sns

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

            binding.etEmail.hint = getString(putEmail)
            binding.etPw.hint = getString(putPassword)
            binding.btnLogin.text = getString(login)
            binding.btnRegister.text = getString(register)

//            val confirmEmail = if (isEnglish) R.string.confirm_email else R.string.confirm_email1
//            val loginSucceed = if (isEnglish) R.string.login_succeed else R.string.login_succeed1
//            val unregisteredEmail = if (isEnglish) R.string.unregistered_email else R.string.unregistered_email1
//            var text1 = "아이디와 비밀번호를 확인해 주세요"
//            text1 = getString(confirmEmail)
//            var text2 = "로그인 성공"
//            text2 = getString(loginSucceed)
//            var text3 = "등록되지 않은 이메일입니다"
//            text3 = getString(unregisteredEmail)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPw.text.toString()

            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (UserManager.isUserRegistered(email, password)) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RegisterActivity::class.java)
                // 임시로 Main 대신 RegisterActivity로 이동
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                Toast.makeText(this, "등록되지 않은 사용자입니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
