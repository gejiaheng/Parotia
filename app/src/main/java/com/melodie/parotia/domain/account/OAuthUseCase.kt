package com.melodie.parotia.domain.account

import com.melodie.parotia.api.service.AuthService
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.AccessToken
import kotlinx.coroutines.CoroutineDispatcher

class OAuthUseCase(
    private val service: AuthService,
    dispatcher: CoroutineDispatcher
) : UseCase<String, AccessToken>(dispatcher) {

    override suspend fun execute(parameters: String): AccessToken {
        return service.getToken(code = parameters)
    }
}
