package com.melodie.parotia.ui.profile.collection

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.R
import com.melodie.parotia.model.Collection
import com.melodie.parotia.ui.list.CollectionPagingAdapter
import com.melodie.parotia.ui.profile.UserContentFragment
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCollectionFragment :
    UserContentFragment<Collection, CollectionPagingAdapter.ViewHolder>() {

    private val viewModel: UserCollectionViewModel by viewModels()

    override fun setupRecyclerView(view: RecyclerView) {
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager = GridLayoutManager(
            context,
            context?.resources?.getInteger(R.integer.collection_spans) ?: 2
        )
    }

    override fun createAdapter(): PagingDataAdapter<Collection, CollectionPagingAdapter.ViewHolder> {
        return CollectionPagingAdapter()
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.getCollections().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
