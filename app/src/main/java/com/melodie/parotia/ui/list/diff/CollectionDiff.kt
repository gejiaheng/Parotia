package com.melodie.parotia.ui.list.diff

import androidx.recyclerview.widget.DiffUtil
import com.melodie.parotia.model.Collection

object CollectionDiff : DiffUtil.ItemCallback<Collection>() {

    override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean =
        oldItem == newItem
}
