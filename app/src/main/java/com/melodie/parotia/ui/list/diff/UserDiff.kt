package com.melodie.parotia.ui.list.diff

import androidx.recyclerview.widget.DiffUtil
import com.melodie.parotia.model.User

object UserDiff : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
