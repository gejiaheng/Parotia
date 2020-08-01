package com.melodie.parotia.ui.profile.collection

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.R
import com.melodie.parotia.model.Collection
import com.melodie.parotia.ui.list.CollectionAdapter
import com.melodie.parotia.ui.profile.ProfileViewModel
import com.melodie.parotia.ui.profile.UserContentFragment
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCollectionFragment : UserContentFragment<Collection, CollectionAdapter.ViewHolder>() {

    private val profileViewModel: ProfileViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private val viewModel: UserCollectionViewModel by viewModels(
        factoryProducer = { UserCollectionViewModelFactory(profileViewModel.username) }
    )

    override fun setupRecyclerView(view: RecyclerView) {
        view.addItemDecoration(SpacingDecoration())
        view.layoutManager = GridLayoutManager(
            context,
            context?.resources?.getInteger(R.integer.collection_spans) ?: 2
        )
    }

    override fun createAdapter(): PagingDataAdapter<Collection, CollectionAdapter.ViewHolder> {
        return CollectionAdapter()
    }

    override fun subscribe() {
        profileViewModel.username.observe(
            viewLifecycleOwner,
            Observer { name ->
                if (!name.isNullOrEmpty()) {
                    lifecycleScope.launch {
                        viewModel.getCollections().collectLatest {
                            adapter.submitData(it)
                        }
                    }
                }
            }
        )
    }
}
