package com.melodie.parotia.ui.search.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentSearchEntryBinding
import com.melodie.parotia.ui.search.history.HistoryColor
import com.melodie.parotia.ui.search.history.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchEntryFragment : Fragment() {

    private lateinit var binding: FragmentSearchEntryBinding
    private val entryViewModel: SearchEntryViewModel by viewModels()
    private val historyViewModel: SearchHistoryViewModel by activityViewModels()

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
        binding.viewModel = entryViewModel
        binding.searchBarClick = View.OnClickListener {
            entryViewModel.startSearch(binding, null)
        }
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
                        entryViewModel.startSearch(binding, chip.text.toString())
                    }
                    binding.historyGroup.addView(chip)
                }
            }
        )
    }
}
