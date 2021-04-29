package aop.fastcampus.part5.chapter02.data.repository

import aop.fastcampus.part5.chapter02.data.entity.product.ProductEntity
import aop.fastcampus.part5.chapter02.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val ioDispatcher: CoroutineDispatcher
): ProductRepository {

    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        val response = productApi.getProducts()
        return@withContext if (response.isSuccessful) {
            response.body()?.items?.map { it.toEntity() } ?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductItem(id: Long) {
        TODO("Not yet implemented")
    }


}
