package com.melodie.parotia.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.base.R

class SpacingDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = view.resources.getDimensionPixelSize(R.dimen.item_spacing)
        outRect.set(spacing, spacing, spacing, spacing)
    }
}
