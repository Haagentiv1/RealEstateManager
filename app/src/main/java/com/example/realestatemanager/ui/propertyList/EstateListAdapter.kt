package com.example.realestatemanager.ui.propertyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.data.model.Estate
import com.example.realestatemanager.databinding.PropertyItemListFragmentBinding

class EstateListAdapter(
    private val listener: (id: String) -> Unit
) : ListAdapter<Estate, EstateListAdapter.EstateViewHolder>(EstateComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder =
        EstateViewHolder(
            PropertyItemListFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {

    }

    class EstateViewHolder(private val binding: PropertyItemListFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EstateListItemViewState, listener: (id: String) -> Unit) {

        }
    }

    class EstateComparator : DiffUtil.ItemCallback<Estate>() {
        override fun areContentsTheSame(oldItem: Estate, newItem: Estate): Boolean =
            oldItem.id == newItem.id

        override fun areItemsTheSame(oldItem: Estate, newItem: Estate): Boolean = oldItem == newItem

    }


}