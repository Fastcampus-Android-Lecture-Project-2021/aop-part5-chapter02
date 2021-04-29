package aop.fastcampus.part5.chapter02.presentation.list

import aop.fastcampus.part5.chapter02.data.entity.product.ProductEntity

internal sealed class ProductListState {

    object UnInitialized: ProductListState()

    object Loading: ProductListState()

    data class Success(
        val productList: List<ProductEntity>
    ): ProductListState()

    object Error: ProductListState()

}
