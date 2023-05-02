package com.example.check24

import com.example.check24.fake.FakeSharedPreference
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
@Config(sdk = [25], application = HiltTestApplication::class )
@LooperMode(LooperMode.Mode.PAUSED)
class TestPreference {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val fakePreference : FakeSharedPreference = FakeSharedPreference()

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @After
    fun destroy(){
        fakePreference.destroy()
    }


    @Test
    fun `test token insert`() {
        fakePreference.saveToken("test+token")
        Assert.assertTrue(fakePreference.getToken() == "test+token")
    }

    @Test
    fun `test token delete`() {
        fakePreference.clearToken()
        Assert.assertTrue(fakePreference.getToken().isEmpty())
    }
}