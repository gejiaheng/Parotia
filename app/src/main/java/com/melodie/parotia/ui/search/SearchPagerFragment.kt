package com.melodie.parotia.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentSearchPagerBinding
import com.melodie.parotia.ui.search.collection.SearchCollectionFragment
import com.melodie.parotia.ui.search.history.HistoryColor
import com.melodie.parotia.ui.search.history.SearchHistoryViewModel
import com.melodie.parotia.ui.search.photo.SearchPhotoFragment
import com.melodie.parotia.ui.search.user.SearchUserFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchPagerFragment : Fragment() {

    private lateinit var binding: FragmentSearchPagerBinding
    private val pagerViewModel: SearchPagerViewModel by viewModels()
    private val historyViewModel: SearchHistoryViewModel by activityViewModels()
    private val args: SearchPagerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPagerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Search bar
        binding.searchInput.doAfterTextChanged {
            pagerViewModel.onQueryTextChange(it?.toString())
        }
        binding.searchInput.apply {
            doAfterTextChanged {
                pagerViewModel.onQueryTextChange(it?.toString())
            }
            setOnEditorActionListener { view, actionId, event ->
                dismissKeyboard(view)
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pagerViewModel.onQueryTextSubmit(this@apply.text.toString())
                    historyViewModel.onQueryTextSubmit(this@apply.text.toString())
                    true
                } else {
                    false
                }
            }
        }
        pagerViewModel.onQueryTextChange(args.query)
        if (!args.query.isNullOrEmpty()) {
            pagerViewModel.onQueryTextSubmit(args.query!!)
            historyViewModel.onQueryTextSubmit(args.query!!)
        }

        // ViewPager
        binding.pager.adapter = SearchPagerAdapter(this)
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            tab.text = getString(SearchType.fromPosition(position).title)
        }.attach()

        binding.viewModel = pagerViewModel
        historyViewModel.history.observe(
            viewLifecycleOwner,
            Observer { history ->
                binding.historyGroup.removeAllViews()
                history.forEach {
                    val chip = LayoutInflater.from(context)
                        .inflate(R.layout.item_search_history, binding.historyGroup, false) as Chip
                    chip.text = it
                    val hc = HistoryColor.randColor()
                    chip.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            hc.color
                        )
                    )
                    chip.setChipBackgroundColorResource(hc.colorBg)
                    chip.setOnClickListener {
//                        binding.searchInput.text = chip.text
                    }
                    binding.historyGroup.addView(chip)
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        dismissKeyboard(binding.searchInput)
    }

    private fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    private fun dismissKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

enum class SearchType(@StringRes val title: Int) {
    PHOTO(R.string.search_type_photo),
    COLLECTION(R.string.search_type_collection),
    USER(R.string.search_type_user);

    companion object {
        fun fromPosition(pos: Int): SearchType {
            return when (pos) {
                0 -> PHOTO
                1 -> COLLECTION
                2 -> USER
                else -> throw IllegalArgumentException("Invalid position argument")
            }
        }
    }
}

class SearchPagerAdapter @Inject constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchPhotoFragment()
            1 -> SearchCollectionFragment()
            2 -> SearchUserFragment()
            else -> throw IllegalArgumentException("Illegal position argument")
        }
    }
}
