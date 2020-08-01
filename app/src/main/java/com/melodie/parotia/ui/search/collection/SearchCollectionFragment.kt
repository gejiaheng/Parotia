package com.melodie.parotia.ui.search.collection

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.model.Collection
import com.melodie.parotia.ui.list.CollectionAdapter
import com.melodie.parotia.ui.search.SearchListFragment
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCollectionFragment : SearchListFragment<Collection, CollectionAdapter.ViewHolder>() {
    override val viewModel: SearchCollectionViewModel by viewModels()

    override fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager = GridLayoutManager(context, 2)
    }

    override fun createAdapter(): PagingDataAdapter<Collection, CollectionAdapter.ViewHolder> {
        return CollectionAdapter()
    }

    override fun onSearch(query: String?) {
        query ?: return
        lifecycleScope.launch {
            viewModel.searchCollection(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
