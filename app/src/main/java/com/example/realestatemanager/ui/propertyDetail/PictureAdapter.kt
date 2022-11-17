package com.example.realestatemanager.ui.propertyDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestatemanager.databinding.PropertyDetailItemFragmentBinding

class PictureAdapter : ListAdapter<Pair<String,String>, PictureAdapter.PictureViewHolder>(PictureComparator()) {


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
        fun bind(item: Pair<String,String>) {
            Glide.with(binding.root).load(item.first).centerCrop().into(binding.propertyDetailItemPicture)
            binding.propertyDetailPictureDesc.text = item.second
        }
    }


    class PictureComparator : DiffUtil.ItemCallback<Pair<String,String>>() {
        override fun areItemsTheSame(
            oldItem: Pair<String, String>,
            newItem: Pair<String, String>
        ): Boolean = oldItem == newItem
        override fun areContentsTheSame(
            oldItem: Pair<String, String>,
            newItem: Pair<String, String>
        ): Boolean = oldItem == newItem

    }

}