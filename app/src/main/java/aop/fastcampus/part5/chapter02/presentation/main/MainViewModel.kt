package aop.fastcampus.part5.chapter02.presentation.main

import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel: BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {

    }

}
