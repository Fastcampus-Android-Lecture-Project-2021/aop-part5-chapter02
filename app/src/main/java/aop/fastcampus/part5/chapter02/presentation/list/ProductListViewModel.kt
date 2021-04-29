package aop.fastcampus.part5.chapter02.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter02.domain.product.GetProductListUseCase
import aop.fastcampus.part5.chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductListViewModel(
    val getProductListUseCase: GetProductListUseCase
): BaseViewModel() {

    private var _productListStateLiveData = MutableLiveData<ProductListState>(ProductListState.UnInitialized)
    val productListStateLiveData: LiveData<ProductListState> = _productListStateLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _productListStateLiveData.postValue(ProductListState.Loading)
        _productListStateLiveData.postValue(ProductListState.Success(getProductListUseCase()))
    }

}
