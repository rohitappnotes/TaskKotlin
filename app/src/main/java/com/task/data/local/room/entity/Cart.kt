package com.task.data.local.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "cart",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf("product_id"),
        childColumns = arrayOf("product_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    val cartId : Int,

    @ColumnInfo(name = "product_id")
    val productId : Int,

    @ColumnInfo(name = "quantity")
    val quantity : Int,

    @ColumnInfo(name = "total_price")
    val totalPrice: Int,

    @ColumnInfo(name = "created_at")
    val createdAt : Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt : Date
): Parcelable