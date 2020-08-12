package com.melodie.parotia.ui.search.entry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.R
import com.melodie.parotia.databinding.FragmentSearchEntryBinding
import com.melodie.parotia.domain.search.GetSearchBannersUseCase
import com.melodie.parotia.domain.search.SaveSearchBannersUseCase
import com.melodie.parotia.domain.stats.GetStatsUseCase
import com.melodie.parotia.model.SearchBanner
import com.melodie.parotia.model.Stats
import com.melodie.parotia.result.data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchEntryViewModel @ViewModelInject constructor(
    private val getStatsUseCase: GetStatsUseCase,
    private val getSearchBannersUseCase: GetSearchBannersUseCase,
    private val saveSearchBannersUseCase: SaveSearchBannersUseCase
) : ViewModel() {

    lateinit var totalStats: LiveData<Stats>

    private val _banners: MutableList<SearchBanner> = mutableListOf()
    val banner: LiveData<SearchBanner> = liveData(Dispatchers.IO) {
        val banners = getSearchBannersUseCase(Unit).data
        if (banners.isNullOrEmpty()) return@liveData
        _banners.addAll(banners)
        var index = 0
        while (true) {
            if (index >= banners.size) {
                index = 0
            }
            emit(_banners[index])
            _banners[index].shown = true
            index++
            delay(BANNER_INTERVAL)
        }
    }

    init {
//        viewModelScope.launch {
//            totalStats = getStatsUseCase(Unit).data!!.distinctUntilChanged()
//        }
    }

    fun startSearch(
        binding: FragmentSearchEntryBinding
    ) {
        val action = MainNavDirections.actionGlobalSearch(null)
        val context = binding.root.context
        val extras = FragmentNavigatorExtras(
            binding.transitionLayout to context.getString(R.string.shared_search_layout),
            binding.searchBar to context.getString(R.string.shared_search_bar),
            binding.searchHintText to context.getString(R.string.shared_search_hint_text),
            binding.historyGroup to context.getString(R.string.shared_search_history_group)
        )
        binding.root.findNavController().navigate(action, extras)
    }

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            saveSearchBannersUseCase(_banners)
        }
    }

    companion object {
        const val BANNER_INTERVAL = 30000L
    }
}
