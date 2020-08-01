package com.melodie.parotia.ui.search.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.search.SearchUserUseCase
import com.melodie.parotia.model.User
import kotlinx.coroutines.flow.Flow

class SearchUserViewModel @ViewModelInject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {
    fun searchUser(query: String): Flow<PagingData<User>> = searchUserUseCase(query)
}
