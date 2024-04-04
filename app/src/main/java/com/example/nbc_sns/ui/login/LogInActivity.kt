package com.example.nbc_sns.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.R
import com.example.nbc_sns.UserManager
import com.example.nbc_sns.UserManager.register
import com.example.nbc_sns.databinding.ActivityLogInBinding
import com.example.nbc_sns.model.UserInfo
//import com.example.nbc_sns.ui.home.MainActivity
import com.example.nbc_sns.ui.register.RegisterActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityLogInBinding

    private var isEnglish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEng.setOnClickListener {

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
        }

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val id = result.data?.getStringExtra("id") ?: ""
                val pw = result.data?.getStringExtra("pw") ?: ""
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pw = binding.etPw.text.toString()
            val userInfo = UserInfo(email, pw, nickName = null)
            val loginSuccess = register(userInfo)

            if (email.isNullOrBlank() || pw.isNullOrBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else if (pw.length < 8) {
                Toast.makeText(this, "비밀번호는 8자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (loginSuccess) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", email)
                intent.putExtra("pw", pw)
                startActivity(intent)
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}