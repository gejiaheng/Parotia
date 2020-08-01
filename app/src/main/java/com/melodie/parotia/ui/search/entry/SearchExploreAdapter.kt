package com.melodie.parotia.ui.search.entry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.databinding.ItemSearchExploreBinding
import com.melodie.parotia.model.SearchExplore

class SearchExploreAdapter() :
    ListAdapter<SearchExplore, SearchExploreAdapter.ViewHolder>(SearchExploreDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemSearchExploreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(explore: SearchExplore) {
            binding.explore = explore
            binding.root.setOnClickListener {
                val action = MainNavDirections.actionGlobalSearch(explore.title)
                binding.root.findNavController().navigate(action)
            }
        }
    }
}

object SearchExploreDiff : DiffUtil.ItemCallback<SearchExplore>() {
    override fun areItemsTheSame(oldItem: SearchExplore, newItem: SearchExplore): Boolean {
        return oldItem.title == newItem.title &&
            oldItem.imageRes == newItem.imageRes &&
            oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: SearchExplore, newItem: SearchExplore): Boolean {
        return oldItem == newItem
    }
}
