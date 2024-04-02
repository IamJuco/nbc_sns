package com.example.nbc_sns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.example.nbc_sns.databinding.ActivityLogInBinding

private val ViewBinding.btnRegister: Any
    get() {
        TODO("Not yet implemented")
    }
private val ViewBinding.btnBack: Any
    get() {
        TODO("Not yet implemented")
    }

private fun Any.setOnClickListener(function: () -> Unit) {
    TODO("Not yet implemented")
}


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater) // 바인딩 인스턴스 초기화
        setContentView(binding.root)

//        binding.tvTop
//
//        binding.ivTeam
//

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}