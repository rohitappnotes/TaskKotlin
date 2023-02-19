package com.task.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.task.data.local.room.entity.Cart
import com.task.data.local.room.entity.Product

data class ProductWithCart (
    @Embedded val product : Product,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "product_id"
    )
    val cart : Cart?
)
