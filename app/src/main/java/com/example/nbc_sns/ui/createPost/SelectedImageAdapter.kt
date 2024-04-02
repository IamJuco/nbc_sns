package com.example.nbc_sns.ui.createPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_sns.databinding.ItemSelectedImageBinding

class SelectedImageAdapter: ListAdapter<GalleryItem, SelectedImageAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemSelectedImageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(galleryItem: GalleryItem) {
            binding.imageViewThumbnail.setImageURI(galleryItem.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSelectedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}