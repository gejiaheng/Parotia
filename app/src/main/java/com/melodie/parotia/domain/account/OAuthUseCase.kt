package com.melodie.parotia.domain.account

import com.melodie.parotia.api.service.AuthService
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.AccessToken
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class OAuthUseCase @Inject constructor(
    private val service: AuthService,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, AccessToken>(dispatcher) {

    override suspend fun execute(parameters: String): AccessToken {
        return service.getToken(code = parameters)
    }
}
