package com.melodie.parotia.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.ItemUserBinding
import com.melodie.parotia.model.User
import com.melodie.parotia.ui.list.diff.UserDiff

class UserPagingAdapter : PagingDataAdapter<User, UserPagingAdapter.ViewHolder>(
    UserDiff
) {

    class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?) {
            binding.userItem = user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
