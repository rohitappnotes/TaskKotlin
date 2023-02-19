package com.task.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.data.local.room.entity.Cart
import java.util.*

@Dao
interface CartDao {
    /*============================================ Insert ========================================*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart): Long
    /*============================================ Update ========================================*/
    @Query("UPDATE cart SET quantity=:quantity, total_price= :totalPrice, updated_at= :updatedAt WHERE cart_id = :cartId")
    fun updateQuantity(quantity: Int, totalPrice: Int, updatedAt: Date, cartId: Int): Int
    /*============================================= Read =========================================*/
    @Query("SELECT * FROM cart")
    fun getCartProduct(): LiveData<List<Cart>>

    @Query("SELECT COUNT(cart_id) FROM cart")
    fun getNumberOfProductAddedIntoCart(): LiveData<Int>
    /*============================================ Delete ========================================*/
    @Query("DELETE FROM cart WHERE cart_id = :cartId")
    fun deleteProductFromCart(cartId: Int): Int
}