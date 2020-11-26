package com.melodie.parotia.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.FragmentSearchListBinding
import com.melodie.parotia.ui.list.ParotiaLoadStateAdapter

abstract class SearchListFragment<T : Any, VH : RecyclerView.ViewHolder> : Fragment() {
    protected lateinit var binding: FragmentSearchListBinding
    private val pagerViewModel: SearchPagerViewModel by viewModels({ requireParentFragment() })
    protected abstract val viewModel: ViewModel
    protected lateinit var adapter: PagingDataAdapter<T, VH>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerViewModel.queryTextSubmitted.observe(
            viewLifecycleOwner,
            Observer { query ->
                run {
                    Log.d("SearchPhotoFragment", query ?: "null")
                    onSearch(query)
                }
            }
        )

        setupRecyclerView(binding.recyclerView)
        adapter = createAdapter()
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            ParotiaLoadStateAdapter { adapter.retry() }
        )
        binding.setRetryClick { adapter.retry() }
        adapter.addLoadStateListener { loadState ->
            binding.recyclerView.isVisible =
                loadState.source.refresh is LoadState.NotLoading || adapter.itemCount > 0

            binding.progressBar.isVisible =
                loadState.source.refresh is LoadState.Loading && adapter.itemCount == 0
            binding.retryButton.isVisible =
                loadState.source.refresh is LoadState.Error && adapter.itemCount == 0

            // Toast on any error
            val errorState = loadState.source.refresh as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    abstract fun setupRecyclerView(view: RecyclerView)

    abstract fun createAdapter(): PagingDataAdapter<T, VH>

    abstract fun onSearch(query: String?)
}
