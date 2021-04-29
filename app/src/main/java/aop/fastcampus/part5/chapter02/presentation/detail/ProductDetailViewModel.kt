package aop.fastcampus.part5.chapter02.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter02.domain.product.GetProductListUseCase
import aop.fastcampus.part5.chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductDetailViewModel(
): BaseViewModel() {

    override fun fetchData(): Job {
        TODO("Not yet implemented")
    }


}
