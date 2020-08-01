package com.melodie.parotia.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.melodie.parotia.domain.search.GetSearchHistoryUseCase
import com.melodie.parotia.domain.search.SaveSearchHistoryUseCase
import com.melodie.parotia.result.data
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPagerViewModel @ViewModelInject constructor(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase
) : ViewModel() {

    private val _queryTextSubmitted: MutableLiveData<String> = MutableLiveData()
    val queryTextSubmitted: LiveData<String> = _queryTextSubmitted
    private val _queryText: MutableLiveData<String> = MutableLiveData()

    val showHistory: LiveData<Boolean> = liveData {
        emit(true)
        emitSource(map(_queryText) { it.isNullOrEmpty() })
    }

    private val _history: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val history: LiveData<List<String>> = _history.distinctUntilChanged()

    init {
        viewModelScope.launch {
            _history.value = getSearchHistoryUseCase(Unit).data
        }
    }

    @ExperimentalStdlibApi
    fun onQueryTextSubmit(query: String) {
        _queryTextSubmitted.value = query
        // TODO("is there any better approach?")
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

    fun onQueryTextChange(query: String) {
        _queryText.value = query
    }

    override fun onCleared() {
        // TODO("should we do this? maybe use WorkManager?")
        if (_history.value!!.isNotEmpty()) {
            GlobalScope.launch {
                saveSearchHistoryUseCase(_history.value!!)
            }
        }
        super.onCleared()
    }

    companion object {
        const val HISTORY_SIZE = 20
    }
}
