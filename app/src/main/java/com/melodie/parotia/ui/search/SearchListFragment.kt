package com.melodie.parotia.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.FragmentSearchListBinding

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
        setupRecyclerView(binding.recyclerView)
        adapter = createAdapter()
        binding.recyclerView.adapter = adapter
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
    }

    abstract fun setupRecyclerView(view: RecyclerView)

    abstract fun createAdapter(): PagingDataAdapter<T, VH>

    abstract fun onSearch(query: String?)
}
