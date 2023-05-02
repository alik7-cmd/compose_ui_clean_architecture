package com.example.check24.common.utils

import com.example.check24.common.preference.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(private val sharedPreference: SharedPreference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(request().newBuilder()
            .addHeader("", sharedPreference.getToken())
            .build())
    }
}