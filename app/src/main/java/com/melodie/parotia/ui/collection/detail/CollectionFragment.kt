package com.melodie.parotia.ui.collection.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentCollectionBinding
import com.melodie.parotia.ui.list.HorizontalPhotoAdapter
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CollectionFragment : Fragment() {

    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding

    @Inject
    lateinit var adapter: HorizontalPhotoAdapter

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
        setupRecyclerView(binding.recyclerViewPhotos)
        binding.viewModel = viewModel
        lifecycleScope.launchWhenResumed {
            viewModel.photos?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager =
            StaggeredGridLayoutManager(
                context?.resources?.getInteger(R.integer.collection_photos_span) ?: 2,
                StaggeredGridLayoutManager.HORIZONTAL
            )
        view.adapter = adapter
    }
}
