package com.melodie.parotia.ui.search.photo

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.model.Photo
import com.melodie.parotia.ui.list.PhotoAdapter
import com.melodie.parotia.ui.search.SearchListFragment
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchPhotoFragment : SearchListFragment<Photo, PhotoAdapter.ViewHolder>() {
    override val viewModel: SearchPhotoViewModel by viewModels()

    override fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager =
            StaggeredGridLayoutManager(
                context?.resources?.getInteger(R.integer.photo_spans) ?: 2,
                StaggeredGridLayoutManager.VERTICAL
            )
    }

    override fun createAdapter(): PagingDataAdapter<Photo, PhotoAdapter.ViewHolder> {
        return PhotoAdapter()
    }

    override fun onSearch(query: String?) {
        query ?: return
        lifecycleScope.launch {
            viewModel.searchPhoto(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
