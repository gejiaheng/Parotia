package com.melodie.parotia.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class SearchPagerViewModel @ViewModelInject constructor() : ViewModel() {

    private val _queryTextSubmitted: MutableLiveData<String> = MutableLiveData()
    val queryTextSubmitted: LiveData<String> = _queryTextSubmitted
    private val _queryText: MutableLiveData<String> = MutableLiveData()

    val showPager: LiveData<Boolean> = liveData {
        emit(false)
        emitSource(map(_queryText) { !it.isNullOrEmpty() })
    }

    val inputEmpty: LiveData<Boolean> = map(_queryText) { it.isNullOrEmpty() }

    fun onQueryTextSubmit(query: String) {
        _queryTextSubmitted.value = query
    }

    fun onQueryTextChange(query: String?) {
        _queryText.value = query
    }
}
