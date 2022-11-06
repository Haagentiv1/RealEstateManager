package com.example.realestatemanager.ui.propertyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.realestatemanager.data.model.Estate
import com.example.realestatemanager.databinding.PropertyItemListFragmentBinding
import javax.inject.Inject

class EstateListAdapter @Inject constructor(
    private val glide: RequestManager,
    private val listener: (id: String) -> Unit
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
        holder.bind(getItem(position), listener,glide)
    }

    class EstateViewHolder(private val binding: PropertyItemListFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EstateListItemViewState, listener: (id: String) -> Unit,glide: RequestManager) {
            glide.load(item.picture).into(binding.propertyItemTvPropertyPicture)
            binding.propertyItemTvPropertyType.text = item.type
            binding.propertyItemTvPropertyTown.text = item.town
            binding.propertyItemTvPropertyPrice.text = item.price
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