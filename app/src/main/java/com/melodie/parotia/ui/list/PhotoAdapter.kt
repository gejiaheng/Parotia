package com.melodie.parotia.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.databinding.ItemPhotoBinding
import com.melodie.parotia.model.Photo
import javax.inject.Inject

class PhotoAdapter @Inject constructor() : PagingDataAdapter<Photo, PhotoAdapter.ViewHolder>(
    PHOTO_DIFF
) {

    private val set = ConstraintSet()

    class ViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?, set: ConstraintSet) {
            binding.photoItem = photo
            // https://github.com/burhanrashid52/AspectRatioExample
            if (photo != null) {
                val ratio = String.format("%d:%d", photo.width, photo.height)
                set.clone(binding.photoFrame)
                set.setDimensionRatio(binding.image.id, ratio)
                set.applyTo(binding.photoFrame)
            }
            binding.photoClick = View.OnClickListener {
                val action = MainNavDirections.actionGlobalPhoto(photo?.urls!!.full)
                binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), set)
    }

    companion object {
        private val PHOTO_DIFF = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }
}
