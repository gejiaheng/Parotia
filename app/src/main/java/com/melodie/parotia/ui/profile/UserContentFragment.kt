package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.FragmentUserContentBinding
import com.melodie.parotia.ui.list.ParotiaLoadStateAdapter

abstract class UserContentFragment<T : Any, VH : RecyclerView.ViewHolder> : Fragment() {
    protected lateinit var binding: FragmentUserContentBinding
    protected lateinit var adapter: PagingDataAdapter<T, VH>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserContentBinding.inflate(inflater, container, false)
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribe()
    }

    abstract fun setupRecyclerView(view: RecyclerView)

    abstract fun createAdapter(): PagingDataAdapter<T, VH>

    abstract fun subscribe()
}
