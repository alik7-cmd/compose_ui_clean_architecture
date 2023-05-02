package com.example.check24.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.check24.BuildConfig
import com.example.check24.overview.data.dao.ProductOverviewDao
import com.example.check24.overview.domain.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class CheckLocalDatabase : RoomDatabase(){

    abstract fun productOverviewDao() : ProductOverviewDao

    companion object {

        private const val DB_NAME = BuildConfig.APPLICATION_ID
        @Volatile
        private var INSTANCE: CheckLocalDatabase? = null


        fun getInstance(context: Context): CheckLocalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): CheckLocalDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                CheckLocalDatabase::class.java, DB_NAME
            ).addCallback(object : Callback() {
            }).allowMainThreadQueries()
                .build()
        }
    }
}