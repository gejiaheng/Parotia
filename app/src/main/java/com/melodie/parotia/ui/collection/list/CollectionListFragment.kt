package com.melodie.parotia.ui.collection.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentCollectionListBinding
import com.melodie.parotia.ui.list.CollectionPagingAdapter
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
        lifecycleScope.launch {
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
        view.adapter = adapter
    }
}
