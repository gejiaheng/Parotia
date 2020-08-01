package com.melodie.parotia.ui.feed.pupular

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.melodie.parotia.ui.feed.FeedListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeedPopularFragment : FeedListFragment() {

    override val viewModel: FeedPopularViewModel by viewModels()

    override fun setupData() {
        lifecycleScope.launchWhenResumed {
            viewModel.photos.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
