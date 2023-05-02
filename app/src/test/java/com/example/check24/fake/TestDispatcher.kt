package com.example.check24.fake

import com.example.check24.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcher : DispatcherProvider {
    override val io: CoroutineDispatcher
        get() = UnconfinedTestDispatcher()
    override val default: CoroutineDispatcher
        get() = UnconfinedTestDispatcher()
    override val main: CoroutineDispatcher
        get() = UnconfinedTestDispatcher()
    override val unconfined: CoroutineDispatcher
        get() = UnconfinedTestDispatcher()
}