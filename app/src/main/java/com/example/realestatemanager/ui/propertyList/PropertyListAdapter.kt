package com.example.realestatemanager.ui.propertyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestatemanager.databinding.PropertyItemListFragmentBinding

class PropertyListAdapter (
    private val listener: (id: Long) -> Unit
) : ListAdapter<PropertyListItemViewState, PropertyListAdapter.EstateViewHolder>(PropertyComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder =
        EstateViewHolder(
            PropertyItemListFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class EstateViewHolder(private val binding: PropertyItemListFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PropertyListItemViewState, listener: (id: Long) -> Unit) {
            Glide.with(binding.root).load(item.picture).centerCrop().into(binding.propertyItemTvPropertyPicture)
            binding.propertyItemTvPropertyType.text = item.type
            binding.propertyItemTvPropertyTown.text = item.town
            binding.propertyItemTvPropertyPrice.text = item.price.toString()
            binding.root.setOnClickListener {
                listener.invoke(item.id)
            }
        }
    }

    class PropertyComparator : DiffUtil.ItemCallback<PropertyListItemViewState>() {
        override fun areContentsTheSame(oldItem: PropertyListItemViewState, newItem: PropertyListItemViewState): Boolean =
            oldItem.id == newItem.id

        override fun areItemsTheSame(oldItem: PropertyListItemViewState, newItem: PropertyListItemViewState): Boolean = oldItem == newItem

    }


}