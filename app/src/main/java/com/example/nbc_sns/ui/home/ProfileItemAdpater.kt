package com.example.nbc_sns.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_sns.databinding.RecyclerviewProfileItemBinding
import com.example.nbc_sns.model.UserInfo

class ProfileItemAdpater(private val items: MutableList<UserInfo>) : RecyclerView.Adapter<ProfileItemAdpater.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerviewProfileItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: RecyclerviewProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserInfo) {
            binding.rvProfileItem.setImageURI(item.thumbnail)

            // 프로필 이미지 클릭 이벤트
            binding.rvProfileItem.setOnClickListener {



            }
        }

    }
}