package com.example.check24.common.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(request().newBuilder()
            .addHeader("", "")
            .build())
    }
}