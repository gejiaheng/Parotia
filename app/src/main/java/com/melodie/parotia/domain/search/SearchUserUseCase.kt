package com.melodie.parotia.domain.search

import androidx.paging.PagingData
import com.melodie.parotia.data.search.SearchUserRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val repository: SearchUserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<String, User>(dispatcher) {
    override fun execute(parameters: String): Flow<PagingData<User>> {
        return repository.getSearchUsersStream(parameters)
    }
}
