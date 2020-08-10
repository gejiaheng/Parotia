package com.melodie.parotia.domain.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.User
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val preference: SharedPreferenceStorage,
    private val repository: UserRepository,
    private val gson: Gson,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Any, LiveData<User?>>(dispatcher) {
    override suspend fun execute(parameters: Any): LiveData<User?> {
        // TODO("should put the following here or in repository?")
        return liveData {
            // get user from local cache
            gson.fromJson(preference.user, User::class.java).apply {
                emit(this)
            }
            // get user from api
            emit(repository.getMe())
        }
    }
}
