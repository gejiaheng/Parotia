package com.melodie.parotia.ui.search.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melodie.parotia.domain.search.GetSearchHistoryUseCase
import com.melodie.parotia.domain.search.SaveSearchHistoryUseCase
import com.melodie.parotia.result.data
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// This ViewModel should be scoped to MainActivity as it is shared between
// SearchEntryFragment and SearchPagerFragment
class SearchHistoryViewModel @ViewModelInject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase
) : ViewModel() {
    private val _history = MutableLiveData<List<String>>()
    val history: LiveData<List<String>> = _history

    @ExperimentalStdlibApi
    fun onQueryTextSubmit(query: String) {
        if (query !in _history.value!!) {
            val newList = mutableListOf<String>()
            newList.add(query)
            newList.addAll(_history.value!!)
            if (_history.value!!.size == HISTORY_SIZE) {
                newList.removeLast()
            }
            _history.value = newList
        }
    }

    init {
        viewModelScope.launch {
            _history.value = getSearchHistoryUseCase(Unit).data
        }
    }

    override fun onCleared() {
        if (history.value!!.isNotEmpty()) {
            GlobalScope.launch {
                saveSearchHistoryUseCase(history.value!!)
            }
        }
        super.onCleared()
    }

    companion object {
        const val HISTORY_SIZE = 20
    }
}
