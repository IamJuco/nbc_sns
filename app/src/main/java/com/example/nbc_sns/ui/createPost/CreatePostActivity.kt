package com.example.nbc_sns.ui.createPost

import android.Manifest
import android.content.ContentUris
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nbc_sns.R
import com.example.nbc_sns.databinding.ActivityCreatePostBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Date


class CreatePostActivity : AppCompatActivity(), GalleryItemSelectListener {

    private lateinit var binding: ActivityCreatePostBinding
    private val galleryAdapter = GalleryAdapter(this)
    private val selectedImageAdapter = SelectedImageAdapter()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getImageFromGallery()
            } else {
                // 사용자에게 권한이 필요한 이유 설명하는 Dialog 띄우기
                showPermissionExplanation()
            }
        }

    override fun update(selectedItems: List<GalleryItem>) {
        binding.tvSelectedImageCount.text = getString(R.string.text_for_selected_image).format(selectedItems.size, GalleryAdapter.MAX_AVAILABLE_SELECTING_COUNT)
        selectedImageAdapter.submitList(selectedItems)
    }

    override fun exceedPossibleCount() {
        Toast.makeText(baseContext,
            getString(R.string.exceed_max_available_count).format(GalleryAdapter.MAX_AVAILABLE_SELECTING_COUNT), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        setListener()
    }

    private fun setRecyclerView() {
        binding.rvGallery.adapter = galleryAdapter
        binding.rvSelectedImage.adapter = selectedImageAdapter
    }

    private fun setListener() {
        binding.ibRequestPermission.setOnClickListener {
            requestGalleryPermission()
        }
    }

    private fun requestGalleryPermission() {
        // 권한 요청
        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        when {
            ContextCompat.checkSelfPermission(
                baseContext,
                readImagePermission
            ) == PackageManager.PERMISSION_GRANTED -> {
                getImageFromGallery()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, readImagePermission) -> {
                requestPermissionLauncher.launch(readImagePermission)
            }

            else -> {
                requestPermissionLauncher.launch(readImagePermission)
            }
        }
    }

    // 권한을 거부한 경우, 안내 문구를 띄우고 사용자가 직접 설정에 들어가서 권한을 설정하도록 유도
    private fun showPermissionExplanation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.image_permission_title))
            .setMessage(getString(R.string.image_permission_message))
            .setNegativeButton(getString(R.string.image_permission_negative)) { dialog: DialogInterface, which: Int ->
            }
            .setPositiveButton(getString(R.string.image_permission_positive)) { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:" + baseContext.packageName)
                }
                startActivity(intent)
            }
            .show()
    }

    private fun getImageFromGallery() {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED)
        val selection = "${MediaStore.Images.Media.MIME_TYPE} in (?,?)"
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val selectionArg = arrayOf(
            mimeTypeMap.getMimeTypeFromExtension("png"),
            mimeTypeMap.getMimeTypeFromExtension("jpg"),
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArg,
            sortOrder
        )
        val list = ArrayList<GalleryItem>()
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val contentUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val dateAdded = it.getLong(dateAddedColumn)
                val date = Date(dateAdded)
                list.add(GalleryItem(id, contentUri, date))
            }
        }
        galleryAdapter.submitList(list)
    }
}