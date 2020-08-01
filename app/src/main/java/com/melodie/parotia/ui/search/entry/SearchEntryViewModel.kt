package com.melodie.parotia.ui.search.entry

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.melodie.parotia.MainNavDirections
import com.melodie.parotia.domain.search.GetSearchExploreUseCase
import com.melodie.parotia.domain.stats.GetStatsUseCase
import com.melodie.parotia.model.SearchExplore
import com.melodie.parotia.model.Stats
import com.melodie.parotia.result.data
import kotlinx.coroutines.launch

class SearchEntryViewModel @ViewModelInject constructor(
    private val getStatsUseCase: GetStatsUseCase,
    private val getSearchExploreUseCase: GetSearchExploreUseCase
) : ViewModel() {

    lateinit var totalStats: LiveData<Stats>

    private val _explores = MutableLiveData<List<SearchExplore>>()
    val explores: LiveData<List<SearchExplore>> = _explores

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
}
