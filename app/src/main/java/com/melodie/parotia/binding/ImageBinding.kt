package com.melodie.parotia.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.melodie.parotia.model.SearchExplore

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("exploreBg")
fun loadExploreBg(view: ImageView, explore: SearchExplore) {
    if (explore.imageRes != null) {
        view.setImageResource(explore.imageRes)
    } else if (explore.imageUrl != null) {
        Glide.with(view.context)
            .load(explore.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}
