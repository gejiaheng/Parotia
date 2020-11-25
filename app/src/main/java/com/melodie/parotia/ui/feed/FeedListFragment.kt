package com.melodie.parotia.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentFeedListBinding
import com.melodie.parotia.ui.list.ParotiaLoadStateAdapter
import com.melodie.parotia.ui.list.PhotoPagingAdapter
import com.melodie.parotia.widget.SpacingDecoration
import javax.inject.Inject

abstract class FeedListFragment : Fragment() {

    protected abstract val viewModel: ViewModel

    @Inject
    lateinit var adapter: PhotoPagingAdapter
    lateinit var binding: FragmentFeedListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(binding.recyclerView)
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        binding.setRetryClick { adapter.retry() }
        setupData()
    }

    private fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager =
            StaggeredGridLayoutManager(
                context?.resources?.getInteger(R.integer.photo_spans) ?: 2,
                StaggeredGridLayoutManager.VERTICAL
            )
        view.adapter = adapter.withLoadStateFooter(
            ParotiaLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Show the list in two cases:
            // 1. refresh succeeds
            // 2. refresh succeeds and user forces a refresh by swiping
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

    protected abstract fun setupData()
}
