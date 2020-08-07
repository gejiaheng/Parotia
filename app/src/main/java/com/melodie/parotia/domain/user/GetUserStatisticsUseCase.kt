package com.melodie.parotia.domain.user

import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.UserStats
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserStatisticsUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, UserStats>(dispatcher) {
    override suspend fun execute(parameters: String): UserStats {
        return repository.getUserStatistics(parameters)
    }
}
