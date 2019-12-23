package io.vinter.wakemeup.utils

import io.vinter.wakemeup.data.preferences.PreferencesRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
        private val preferencesRepository: PreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = preferencesRepository.getToken()
            .takeUnless { it.isEmpty() }
            ?.let { proceedWithToken(chain, it) }
            ?: chain.proceed(chain.request())

    private fun proceedWithToken(chain: Interceptor.Chain, token: String): Response =
            chain.proceed(chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            )
}
