package com.melodie.parotia.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.databinding.ItemCollectionBinding
import com.melodie.parotia.model.Collection
import com.melodie.parotia.ui.list.diff.CollectionDiff
import javax.inject.Inject

class CollectionAdapter @Inject constructor() :
    ListAdapter<Collection, CollectionAdapter.ViewHolder>(CollectionDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

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
}
