package com.example.check24.common

sealed class BaseResult<out T : Any, out E : Any> {
    object Loading : BaseResult<Nothing, Nothing>()
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<E : Any>(val error: E) : BaseResult<Nothing, E>()
}