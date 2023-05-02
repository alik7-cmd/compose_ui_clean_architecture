package com.example.check24

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.check24.common.CheckLocalDatabase
import com.example.check24.overview.dao.ProductOverviewDao
import com.example.check24.overview.domain.entity.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDaoTest {


    private lateinit var database: CheckLocalDatabase
    private lateinit var productDao: ProductOverviewDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CheckLocalDatabase::class.java
        ).allowMainThreadQueries().build()

        productDao = database.productOverviewDao()
    }

    @Test
    fun check_insert() = runBlocking {

        val entity = ProductEntity(true)
        productDao.insert(entity)
        val product = productDao.getAllProduct()
        assertThat(product.contains(entity)).isTrue()
    }

    @Test
    fun check_delete() = runBlocking {
        val e1 = ProductEntity(true)
        val e2 = ProductEntity(true)
        productDao.insert(e1)
        productDao.insert(e2)
        productDao.delete(e1)
        val productList = productDao.getAllProduct()
        assertThat(productList.contains(e1)).isFalse()
    }

    @After
    fun tearDown() {
        database.close()
    }

}