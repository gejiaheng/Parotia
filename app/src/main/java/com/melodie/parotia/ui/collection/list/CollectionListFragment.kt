package com.melodie.parotia.ui.collection.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentCollectionListBinding
import com.melodie.parotia.ui.list.CollectionPagingAdapter
import com.melodie.parotia.ui.list.ParotiaLoadStateAdapter
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CollectionListFragment : Fragment() {

    private val viewModel: CollectionListViewModel by viewModels()

    @Inject
    lateinit var adapter: CollectionPagingAdapter
    private lateinit var binding: FragmentCollectionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(binding.recyclerView)
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        binding.setRetryClick { adapter.retry() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.collections.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.layoutManager = GridLayoutManager(
            context,
            context?.resources?.getInteger(R.integer.collection_spans) ?: 2
        )
        view.addItemDecoration(SpacingDecoration())
        view.adapter = adapter.withLoadStateFooter(
            ParotiaLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.swipeRefresh.isVisible =
                loadState.source.refresh is LoadState.NotLoading || adapter.itemCount > 0

            binding.progressBar.isVisible =
                loadState.source.refresh is LoadState.Loading && adapter.itemCount == 0
            binding.retryButton.isVisible =
                loadState.source.refresh is LoadState.Error && adapter.itemCount == 0

            if (loadState.source.refresh is LoadState.NotLoading ||
                loadState.source.refresh is LoadState.Error
            ) {
                binding.swipeRefresh.isRefreshing = false
            }

            // Toast on any error
            val errorState = loadState.source.refresh as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
