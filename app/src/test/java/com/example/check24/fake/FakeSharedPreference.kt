package com.example.check24.fake

import com.example.check24.common.preference.SharedPreference


class FakeSharedPreference : SharedPreference {

    private val map = mutableMapOf<String, String>()
    private val PREF_KEY = "PREF_KEY"

    override fun saveToken(token: String) {
        map[PREF_KEY] = token
    }

    override fun getToken(): String {
        return map[PREF_KEY] ?: ""
    }

    override fun clearToken() {
        map.remove(PREF_KEY)
    }

    fun destroy(){
        map.clear()
    }
}