package com.task.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.data.local.room.entity.Product
import com.task.data.local.room.relations.ProductWithCart

@Dao
interface ProductDao {
    /*============================================ Insert ========================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product): Long
    /*============================================= Read =========================================*/
    @Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>
    /*==================================== One to One Relationship ===============================*/
    @Transaction
    @Query("SELECT * FROM product")
    fun getProductWithCart(): LiveData<List<ProductWithCart>>
}