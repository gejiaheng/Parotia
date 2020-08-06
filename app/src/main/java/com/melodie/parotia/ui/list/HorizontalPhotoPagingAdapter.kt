package com.melodie.parotia.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.databinding.ItemPhotoHorizontalBinding
import com.melodie.parotia.model.Photo
import com.melodie.parotia.ui.list.diff.PhotoDiff
import javax.inject.Inject

class HorizontalPhotoPagingAdapter @Inject constructor() :
    PagingDataAdapter<Photo, HorizontalPhotoPagingAdapter.ViewHolder>(PhotoDiff) {

    private val set = ConstraintSet()

    class ViewHolder(
        private val binding: ItemPhotoHorizontalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?, set: ConstraintSet) {
            binding.photoItem = photo
            photo?.apply {
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
        val binding =
            ItemPhotoHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), set)
    }
}
