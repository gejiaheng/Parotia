package com.melodie.parotia.ui.collection.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentCollectionBinding
import com.melodie.parotia.ui.list.CollectionAdapter
import com.melodie.parotia.ui.list.HorizontalPhotoPagingAdapter
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CollectionFragment : Fragment() {

    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding

    @Inject
    lateinit var photoAdapter: HorizontalPhotoPagingAdapter

    @Inject
    lateinit var relatedAdapter: CollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewPhotos(binding.recyclerViewPhotos)
        setupRecyclerViewRelated(binding.recyclerViewRelated)
        binding.viewModel = viewModel
        lifecycleScope.launchWhenResumed {
            viewModel.photos?.collectLatest {
                photoAdapter.submitData(it)
            }
        }
        viewModel.relatedCollections?.observe(
            viewLifecycleOwner,
            Observer {
                relatedAdapter.submitList(it)
            }
        )
    }

    private fun setupRecyclerViewPhotos(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager =
            StaggeredGridLayoutManager(
                context?.resources?.getInteger(R.integer.collection_photos_span) ?: 2,
                StaggeredGridLayoutManager.HORIZONTAL
            )
        view.adapter = photoAdapter
    }

    private fun setupRecyclerViewRelated(view: RecyclerView) {
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager = GridLayoutManager(
            context,
            context?.resources?.getInteger(R.integer.collection_spans) ?: 2
        )
        view.adapter = relatedAdapter
    }
}
