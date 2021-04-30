package aop.fastcampus.part5.chapter02.domain.product

import aop.fastcampus.part5.chapter01.domain.UseCase
import aop.fastcampus.part5.chapter02.data.entity.product.ProductEntity
import aop.fastcampus.part5.chapter02.data.repository.ProductRepository

internal class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke() {
        return productRepository.deleteAll()
    }

}
