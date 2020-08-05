package com.melodie.parotia.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentFeedListBinding
import com.melodie.parotia.ui.list.PhotoPagingAdapter
import com.melodie.parotia.widget.SpacingDecoration
import javax.inject.Inject

abstract class FeedListFragment : Fragment() {

    protected abstract val viewModel: ViewModel

    @Inject
    lateinit var adapter: PhotoPagingAdapter
    private lateinit var binding: FragmentFeedListBinding

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
        view.adapter = adapter
    }

    protected abstract fun setupData()
}
