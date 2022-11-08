package com.example.realestatemanager.ui.propertyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.realestatemanager.data.model.Estate
import com.example.realestatemanager.databinding.PropertyItemListFragmentBinding
import javax.inject.Inject

class EstateListAdapter (
    private val listener: (id: Long) -> Unit
) : ListAdapter<EstateListItemViewState, EstateListAdapter.EstateViewHolder>(EstateComparator()) {

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
        fun bind(item: EstateListItemViewState, listener: (id: Long) -> Unit) {
            Glide.with(binding.root).load(item.picture).into(binding.propertyItemTvPropertyPicture)
            binding.propertyItemTvPropertyType.text = item.type
            binding.propertyItemTvPropertyTown.text = item.town
            binding.propertyItemTvPropertyPrice.text = item.price.toString()
            binding.root.setOnClickListener {
                listener.invoke(item.id)
            }
        }
    }

    class EstateComparator : DiffUtil.ItemCallback<EstateListItemViewState>() {
        override fun areContentsTheSame(oldItem: EstateListItemViewState, newItem: EstateListItemViewState): Boolean =
            oldItem.id == newItem.id

        override fun areItemsTheSame(oldItem: EstateListItemViewState, newItem: EstateListItemViewState): Boolean = oldItem == newItem

    }


}