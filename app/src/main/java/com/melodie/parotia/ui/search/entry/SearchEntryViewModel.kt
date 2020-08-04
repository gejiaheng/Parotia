package com.melodie.parotia.ui.search.entry

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.domain.search.GetSearchBannersUseCase
import com.melodie.parotia.domain.search.GetSearchExploreUseCase
import com.melodie.parotia.domain.search.SaveSearchBannersUseCase
import com.melodie.parotia.domain.stats.GetStatsUseCase
import com.melodie.parotia.model.SearchBanner
import com.melodie.parotia.model.SearchExplore
import com.melodie.parotia.model.Stats
import com.melodie.parotia.result.data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchEntryViewModel @ViewModelInject constructor(
    private val getStatsUseCase: GetStatsUseCase,
    private val getSearchBannersUseCase: GetSearchBannersUseCase,
    private val saveSearchBannersUseCase: SaveSearchBannersUseCase,
    private val getSearchExploreUseCase: GetSearchExploreUseCase
) : ViewModel() {

    lateinit var totalStats: LiveData<Stats>

    private val _explores = MutableLiveData<List<SearchExplore>>()
    val explores: LiveData<List<SearchExplore>> = _explores

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
        viewModelScope.launch {
            totalStats = getStatsUseCase(Unit).data!!.distinctUntilChanged()
            _explores.value = getSearchExploreUseCase(Unit).data
        }
    }

    fun startSearch(view: View) {
        val action = MainNavDirections.actionGlobalSearch(null)
        view.findNavController().navigate(action)
    }

    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            saveSearchBannersUseCase(_banners)
        }
    }

    companion object {
        const val BANNER_INTERVAL = 5000L
    }
}
