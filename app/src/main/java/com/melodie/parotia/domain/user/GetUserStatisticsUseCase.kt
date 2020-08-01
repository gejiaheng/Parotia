package com.melodie.parotia.domain.user

import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.UserStats
import kotlinx.coroutines.CoroutineDispatcher

class GetUserStatisticsUseCase(
    private val repository: UserRepository,
    dispatcher: CoroutineDispatcher
) : UseCase<String, UserStats>(dispatcher) {
    override suspend fun execute(parameters: String): UserStats {
        return repository.getUserStatistics(parameters)
    }
}
