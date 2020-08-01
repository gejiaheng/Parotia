package com.melodie.parotia.domain.user

import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.User
import kotlinx.coroutines.CoroutineDispatcher

class GetMeUseCase(
    private val repository: UserRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<Any, User>(dispatcher) {
    override suspend fun execute(parameters: Any): User {
        return repository.getMe()
    }
}
