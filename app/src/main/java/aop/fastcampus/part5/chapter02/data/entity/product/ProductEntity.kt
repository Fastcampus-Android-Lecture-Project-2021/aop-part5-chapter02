package aop.fastcampus.part5.chapter02.data.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
{
"id": "1",
"createdAt": "2021-04-23T19:44:11.102Z",
"product_name": "Handcrafted Fresh Keyboard",
"product_price": "263.00",
"product_image": "http://lorempixel.com/640/480/technics",
"product": "Bike"
},
 */

@Entity
data class ProductEntity(
    @PrimaryKey val id: Long,
    val createdAt: Date,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)
