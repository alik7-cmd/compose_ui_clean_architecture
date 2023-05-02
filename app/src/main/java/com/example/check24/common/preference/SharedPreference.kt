package com.example.check24.common.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.check24.BuildConfig


interface SharedPreference {

    fun saveToken(token: String)
    fun getToken(): String
    fun clearToken()

    /*fun <T> get(key: String, clazz: Class<T>): T
    fun <T> put(key: String, data: T)*/
}

class SharedPreferenceImpl(val context: Context) : SharedPreference {

    companion object {
        private const val PREF_TOKEN = "PREF_TOKEN"
    }

    val sharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }


    /*private val masterKeyAlias: String =
        MasterKey.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPref: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            BuildConfig.APPLICATION_ID,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }*/


    override fun saveToken(token: String) {
        put(PREF_TOKEN, token)
    }

    override fun getToken(): String {
        return get(PREF_TOKEN, String::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    override fun clearToken() {
        sharedPref.edit().run {
            remove(PREF_TOKEN)
        }.apply()
    }
}