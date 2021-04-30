package aop.fastcampus.part5.chapter02.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter02.data.entity.product.ProductEntity
import aop.fastcampus.part5.chapter02.domain.product.GetProductItemUseCase
import aop.fastcampus.part5.chapter02.domain.product.OrderProductItemUseCase
import aop.fastcampus.part5.chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductDetailViewModel(
    private val productId: Long,
    private val getProductItemUseCase: GetProductItemUseCase,
    private val orderProductItemUseCase: OrderProductItemUseCase
): BaseViewModel() {

    private var _productDetailState = MutableLiveData<ProductDetailState>(ProductDetailState.UnInitialized)
    val productDetailState: LiveData<ProductDetailState> = _productDetailState

    private lateinit var productEntity: ProductEntity

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ProductDetailState.Loading)
        getProductItemUseCase(productId)?.let { product ->
            productEntity = product
            setState(
                ProductDetailState.Success(product)
            )
        } ?: kotlin.run {
            setState(ProductDetailState.Error)
        }
    }

    fun orderProduct() = viewModelScope.launch {
        val productId = orderProductItemUseCase(productEntity)
        if (productEntity.id == productId) {
            setState(ProductDetailState.Order)
        }
    }

    private fun setState(state: ProductDetailState) {
        _productDetailState.postValue(state)
    }

}
