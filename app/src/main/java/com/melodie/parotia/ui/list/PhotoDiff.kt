package com.melodie.parotia.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.melodie.parotia.model.Photo

object PhotoDiff : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem == newItem
}
