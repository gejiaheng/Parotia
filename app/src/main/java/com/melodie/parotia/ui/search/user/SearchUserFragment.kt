package com.melodie.parotia.ui.search.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.model.User
import com.melodie.parotia.ui.list.UserAdapter
import com.melodie.parotia.ui.search.SearchListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchUserFragment : SearchListFragment<User, UserAdapter.ViewHolder>() {
    override val viewModel: SearchUserViewModel by viewModels()

    override fun setupRecyclerView(view: RecyclerView) {
        view.setHasFixedSize(true)
        view.layoutManager = LinearLayoutManager(context)
    }

    override fun createAdapter(): PagingDataAdapter<User, UserAdapter.ViewHolder> {
        return UserAdapter()
    }

    override fun onSearch(query: String?) {
        query ?: return
        lifecycleScope.launch {
            viewModel.searchUser(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
