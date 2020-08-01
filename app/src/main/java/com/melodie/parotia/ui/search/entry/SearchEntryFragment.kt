package com.melodie.parotia.ui.search.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.FragmentSearchEntryBinding
import com.melodie.parotia.model.SearchExplore
import com.melodie.parotia.widget.SpacingDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchEntryFragment : Fragment() {

    private lateinit var binding: FragmentSearchEntryBinding
    private val viewModel: SearchEntryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchEntryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.buttonClick = View.OnClickListener {
            viewModel.startSearch(it)
        }
    }
}

@BindingAdapter("exploreItems")
fun exploreItems(recyclerView: RecyclerView, list: List<SearchExplore>?) {
    list ?: return
    if (recyclerView.adapter == null) {
        recyclerView.adapter = SearchExploreAdapter()
    }
    recyclerView.addItemDecoration(SpacingDecoration())
    (recyclerView.adapter as SearchExploreAdapter).apply {
        submitList(list)
    }
}
