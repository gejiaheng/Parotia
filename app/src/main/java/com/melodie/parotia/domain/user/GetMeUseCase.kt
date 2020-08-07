package com.melodie.parotia.domain.user

import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.User
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Any, User>(dispatcher) {
    override suspend fun execute(parameters: Any): User {
        return repository.getMe()
    }
}
