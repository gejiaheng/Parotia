package com.melodie.parotia.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.databinding.ItemCollectionBinding
import com.melodie.parotia.model.Collection
import javax.inject.Inject

class CollectionAdapter @Inject constructor() :
    PagingDataAdapter<Collection, CollectionAdapter.ViewHolder>(
        COLLECTION_DIFF
    ) {

    class ViewHolder(
        private val binding: ItemCollectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: Collection?) {
            binding.collectionItem = collection
            binding.collectionClick = View.OnClickListener {
                val arg = collection?.shrink()
                val action = MainNavDirections.actionGlobalCollection(
                    arg!!
                )
                binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val COLLECTION_DIFF = object : DiffUtil.ItemCallback<Collection>() {
            override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean =
                oldItem == newItem
        }
    }
}
