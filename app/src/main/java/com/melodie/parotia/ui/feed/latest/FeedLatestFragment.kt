package com.melodie.parotia.ui.feed.latest

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.melodie.parotia.ui.feed.FeedListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedLatestFragment : FeedListFragment() {

    override val viewModel: FeedLatestViewModel by viewModels()

    override fun observeUIData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
