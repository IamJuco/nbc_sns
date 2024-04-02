package com.example.nbc_sns.ui.createPost

interface GalleryItemSelectListener {

    fun update(selectedItems: List<GalleryItem>)
    fun exceedPossibleCount()
}