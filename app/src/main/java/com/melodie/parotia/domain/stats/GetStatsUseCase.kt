package com.melodie.parotia.domain.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.melodie.parotia.data.stats.StatsRepository
import com.melodie.parotia.di.MainDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.Stats
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetStatsUseCase @Inject constructor(
    private val repository: StatsRepository,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, LiveData<Stats>>(dispatcher) {
    override suspend fun execute(parameters: Unit): LiveData<Stats> {
        return liveData {
            val cache = repository.getTotalStatsLocal()
            if (cache != null) {
                emit(cache!!)
            }
            val fresh = repository.getTotalStatsRemote()
            emit(fresh)
            repository.saveTotalStatsRemoteToLocal(fresh)
        }
    }
}
