package com.melodie.parotia.ui.feed.pupular

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.melodie.parotia.ui.feed.FeedListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedPopularFragment : FeedListFragment() {

    override val viewModel: FeedPopularViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun setupData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
