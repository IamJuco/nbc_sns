package com.example.nbc_sns

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.databinding.ActivityLogInBinding
import java.util.Locale

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
            val confirmEmail = if (isEnglish) R.string.confirm_email else R.string.confirm_email1
            val loginSucceed = if (isEnglish) R.string.login_succeed else R.string.login_succeed1
            val unregisteredEmail = if (isEnglish) R.string.unregistered_email else R.string.unregistered_email1

            // 가져온 문자열 리소스로 텍스트 업데이트
            binding.etEmail.hint = getString(putEmail)
            binding.etPw.hint = getString(putPassword)
            binding.buttonLogin.text = getString(login)
            binding.buttonRegister.text = getString(register)
            var text1 = "아이디와 비밀번호를 확인해 주세요"
            text1 = getString(confirmEmail)
            var text2 = "로그인 성공"
            text2 = getString(loginSucceed)
            var text3 = "등록되지 않은 이메일입니다"
            text3 = getString(unregisteredEmail)
        }

        binding.tvTop
        binding.ivTeam

        binding.buttonLogin.setOnClickListener {
            val ID = binding.etEmail.text.toString()
            val PW = binding.etPw.text.toString()

            if (ID.isBlank() || PW.isBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()

            } else {
                val email = binding.etEmail.text.toString()
                val password = binding.etPw.text.toString()
                val registeredEmails = listOf("user1@example.com", "user2@example.com", "user3@example.com")

                if (registeredEmails.contains(email)) {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RegisterActivity::class.java)
                    // 임시로 Main 대신 RegisterActivity로 이동
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "등록되지 않은 이메일입니다", Toast.LENGTH_SHORT).show()
                }

                // 로그인 이후 사용자 이름이 화면에서 보이게 하기
//                val userData = UserManager.getUserData()
//                if (userData != null) {
//                    val name = userData.name
//                    val email = userData.email
//                }
            }

            binding.buttonRegister.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}