package com.melodie.parotia.binding

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.melodie.parotia.model.Photo
import com.melodie.parotia.util.BlurHashDecoder

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url ?: return

    Glide.with(view.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

const val PLACEHOLDER_WIDTH = 10

@BindingAdapter("photo")
fun loadPhoto(view: ImageView, photo: Photo?) {
    photo ?: return

    val bitmap = BlurHashDecoder.decode(
        photo.blur_hash,
        PLACEHOLDER_WIDTH,
        photo.height * PLACEHOLDER_WIDTH / photo.width
    )
    Glide.with(view.context)
        .load(photo.suitableUrl(view.width))
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(BitmapDrawable(view.resources, bitmap))
        .into(view)
}
