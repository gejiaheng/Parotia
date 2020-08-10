package com.melodie.parotia.ui.profile.photo

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.model.Photo
import com.melodie.parotia.ui.list.PhotoPagingAdapter
import com.melodie.parotia.ui.profile.UserContentFragment
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserPhotoFragment : UserContentFragment<Photo, PhotoPagingAdapter.ViewHolder>() {

    private val viewModel: UserPhotoViewModel by viewModels()

    override fun setupRecyclerView(view: RecyclerView) {
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager =
            StaggeredGridLayoutManager(
                context?.resources?.getInteger(R.integer.photo_spans) ?: 2,
                StaggeredGridLayoutManager.VERTICAL
            )
    }

    override fun createAdapter(): PagingDataAdapter<Photo, PhotoPagingAdapter.ViewHolder> {
        return PhotoPagingAdapter()
    }

    override fun subscribe() {
        lifecycleScope.launchWhenResumed {
            viewModel.getPhotos().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
