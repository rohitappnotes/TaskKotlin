package com.task.data.repository

import androidx.lifecycle.LiveData
import com.task.data.local.room.dao.CartDao
import com.task.data.local.room.dao.ProductDao
import javax.inject.Inject

public class LocalRepository @Inject constructor(private val productDao: ProductDao, private val cartDao: CartDao) {

    fun getCartCount(): LiveData<Int> {
        return cartDao.getNumberOfProductAddedIntoCart()
    }
}