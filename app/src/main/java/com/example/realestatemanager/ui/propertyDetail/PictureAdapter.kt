package com.example.realestatemanager.ui.propertyDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestatemanager.databinding.PropertyDetailItemFragmentBinding

class PictureAdapter : ListAdapter<String, PictureAdapter.PictureViewHolder>(PictureComparator()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PictureViewHolder(
        PropertyDetailItemFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PictureViewHolder(private val binding: PropertyDetailItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.root).load(item).centerCrop().into(binding.propertyDetailItemPicture)
        }
    }


    class PictureComparator : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

}