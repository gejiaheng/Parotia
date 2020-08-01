package com.melodie.parotia.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentSearchPagerBinding
import com.melodie.parotia.ui.search.collection.SearchCollectionFragment
import com.melodie.parotia.ui.search.photo.SearchPhotoFragment
import com.melodie.parotia.ui.search.user.SearchUserFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchPagerFragment : Fragment() {

    @Inject
    lateinit var searchPagerAdapter: SearchPagerAdapter
    private lateinit var binding: FragmentSearchPagerBinding
    private val viewModel: SearchPagerViewModel by viewModels()
    private val args: SearchPagerFragmentArgs by navArgs()

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
        binding.toolbar.apply {
            // TODO
        }
        binding.pager.adapter = searchPagerAdapter
        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            tab.text = getString(SearchType.fromPosition(position).title)
        }.attach()

        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    dismissKeyboard(this@apply)
                    viewModel.onQueryTextSubmit(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.onQueryTextChange(query)
                    return true
                }
            })

            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            if (!args.query.isNullOrEmpty()) {
                setQuery(args.query, true)
            }
            requestFocus()
        }

        binding.viewModel = viewModel
        viewModel.history.observe(
            viewLifecycleOwner,
            Observer { histories ->
                binding.historyGroup.removeAllViews()
                histories.forEach {
                    val chip = LayoutInflater.from(context).inflate(R.layout.item_search_history, binding.historyGroup, false) as Chip
                    chip.text = it
                    chip.setOnClickListener {
                        binding.searchView.setQuery(chip.text, true)
                    }
                    binding.historyGroup.addView(chip)
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        dismissKeyboard(binding.searchView)
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
