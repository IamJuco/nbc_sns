package com.example.nbc_sns.ui.createPost

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_sns.R
import com.example.nbc_sns.databinding.ActivityCreatePostBinding
import com.example.nbc_sns.model.PostImages
import com.example.nbc_sns.model.SelectedImage
import com.example.nbc_sns.ui.PostManager

class CreatePostActivity : AppCompatActivity(), ImageLocationListener {

    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var selectedImage: SelectedImage
    private lateinit var userId: String

    private val adapter by lazy {
        CreatePostAdapter(this)
    }

    override fun changedTo(position: Int) {
        updateImageIndex(position + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        if (processImageFromIntent().not()) {
            return
        }
        initView()
        setListener()
    }

    private fun setRecyclerView() {
        binding.rvSelectedImage.adapter = adapter
        SnapHelperOneByOne(this).attachToRecyclerView(binding.rvSelectedImage)
    }

    private fun processImageFromIntent(): Boolean {
        val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(BUNDLE_KEY_FOR_CREATE_POST_IMAGE, SelectedImage::class.java)
        } else {
            intent.getParcelableExtra(BUNDLE_KEY_FOR_CREATE_POST_IMAGE)
        } ?: run {
            Toast.makeText(
                baseContext,
                getString(R.string.no_image_passed_to_create_post_activity), Toast.LENGTH_LONG
            )
                .show()
            finish()
            return false
        }
        // TODO : 프로필 화면 만들 때, userId 전달해줘야 함
        userId = intent.getStringExtra(BUNDLE_KEY_FOR_USER_ID) ?: "temp"
        this.selectedImage = selectedImage
        adapter.submitList(selectedImage.images)
        return true
    }

    private fun initView() {
        updateImageIndex(1)
    }

    private fun updateImageIndex(position: Int) {
        binding.textViewImageIndex.text = getString(R.string.current_index_from_all).format(
            position,
            selectedImage.images.size
        )
    }

    private fun setListener() {
        binding.btnSubmit.setOnClickListener {
            PostManager.addPost(binding.editTextContent.text.toString(), PostImages(selectedImage.images.map { it.uri }), userId)
            // 프로필 화면으로 이동하도록 수정
            // 프로필 화면 -> 이미지 선택 화면 -> 여기로 이동했기 때문에,
            // 프로필 화면 start 하면서 backstack에 있는 화면들 지우도록 launchmode 설정하기
        }
    }

    companion object {
        const val BUNDLE_KEY_FOR_CREATE_POST_IMAGE = "KEY_FOR_CREATE_POST_IMAGE"
        const val BUNDLE_KEY_FOR_USER_ID = "KEY_FOR_USER_ID"
    }
}