package aop.fastcampus.part5.chapter02.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

internal abstract class BaseViewModel: ViewModel() {

    abstract fun fetchData(): Job

}
