package com.example.nbc_sns.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nbc_sns.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    companion object {
        const val BUNDLE_KEY_FOR_USER_ID_CHECK = "KEY_FOR_USER_ID_CHECK"
    }
}