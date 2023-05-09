package com.example.check24.overview.data.dao

import androidx.room.*
import com.example.check24.overview.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductOverviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repoEntity: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todos: List<ProductEntity>)

    @Delete
    suspend fun delete(repoEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    fun getAllProduct(): MutableList<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE available = 1")
    fun getAllAvailableProduct(): MutableList<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE isLiked = 1")
    fun getAllFavouriteProduct(): MutableList<ProductEntity>

    @Query("SELECT * FROM ProductEntity")
    fun getAllProductFlow(): Flow<List<ProductEntity>>

    @Query("DELETE FROM ProductEntity")
    suspend fun nukeTable()
}