package com.example.nbc_sns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.example.nbc_sns.databinding.ActivityLogInBinding
import com.example.nbc_sns.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        binding.btnEng.setOnClickListener {
            var isEnglish = false
            isEnglish = !isEnglish

            val name = if (isEnglish) R.string.name else R.string.name1
            val email = if (isEnglish) R.string.email else R.string.email1
            val password = if (isEnglish) R.string.password else R.string.password1
            val rePassword = if (isEnglish)  R.string.re_password else R.string.re_password1
            val putName = if (isEnglish) R.string.put_name else R.string.put_name1
            val putEmail = if (isEnglish) R.string.put_email else R.string.put_email1
            val putPassword = if (isEnglish) R.string.put_password else R.string.put_password1
            val putRePassword = if (isEnglish)  R.string.put_re_password else R.string.put_re_password1
            val register = if (isEnglish) R.string.register else R.string.register1
            val validEmail = if (isEnglish) R.string.valid_email else R.string.valid_email1
            val passwordLength = if (isEnglish) R.string.password_length else R.string.password_length1
            val information = if (isEnglish) R.string.information else R.string.information1
            val passwordMatch = if (isEnglish) R.string.password_match else R.string.password_match1

            binding.etName.hint = getString(putName)
            binding.etEmail.hint = getString(putEmail)
            binding.etPw.hint = getString(putPassword)
            binding.etRePw.hint = getString(putRePassword)
            binding.btnRegister.text = getString(register)

            var text1 = "이름(별명)"
            text1 = getString(name)
            var text2 = "아이디(이메일)"
            text2 = getString(email)
            var text3 = "비밀번호"
            text3 = getString(password)
            var text4 = "비밀번호 확인"
            text4 = getString(rePassword)
            var text5 = "유효한 이메일을 입력해 주세요"
            text5 = getString(putEmail)
            var text6 = "비밀번호는 8자 이상이어야 합니다"
            text6 = getString(putPassword)
            var text7 = "비밀번호가 일치하지 않습니다"
            text7 = getString(putRePassword)
            var text8 = "입력되지 않은 정보가 있습니다"
            text8 = getString(information)
        }

        binding.tvName
        binding.etName
        binding.tvEmail
        binding.etEmail
        binding.tvPw
        binding.etPw
        binding.tvRePw
        binding.etRePw

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val ID = binding.etEmail.text.toString()
            val PW = binding.etPw.text.toString()
            val rePW = binding.etRePw.text.toString()

            val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            val isEmailValid = emailPattern.matches(ID)
            val isPasswordValid = PW.length >= 8

            if (!isEmailValid) {
                Toast.makeText(this, "유효한 이메일을 입력해 주세요", Toast.LENGTH_SHORT).show()

            } else if (!isPasswordValid) {
                Toast.makeText(this, "비밀번호는 8자 이상이어야 합니다", Toast.LENGTH_SHORT).show()

            } else if (PW != rePW) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()

            } else if (name.isBlank() || ID.isBlank() || PW.isBlank() || rePW.isBlank()) {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}