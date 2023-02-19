package com.task.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.data.local.room.converter.DateConverter
import com.task.data.local.room.dao.CartDao
import com.task.data.local.room.dao.ProductDao
import com.task.data.local.room.entity.Cart
import com.task.data.local.room.entity.Product

@Database(entities = [Product::class, Cart::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}