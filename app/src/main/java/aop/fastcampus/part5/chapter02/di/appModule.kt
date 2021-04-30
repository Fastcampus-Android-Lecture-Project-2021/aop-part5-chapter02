package aop.fastcampus.part5.chapter02.di

import aop.fastcampus.part5.chapter02.data.db.provideDB
import aop.fastcampus.part5.chapter02.data.db.provideToDoDao
import aop.fastcampus.part5.chapter02.data.network.*
import aop.fastcampus.part5.chapter02.data.network.provideProductRetrofit
import aop.fastcampus.part5.chapter02.data.preference.PreferenceManager
import aop.fastcampus.part5.chapter02.data.repository.DefaultProductRepository
import aop.fastcampus.part5.chapter02.data.repository.ProductRepository
import aop.fastcampus.part5.chapter02.domain.product.*
import aop.fastcampus.part5.chapter02.domain.product.DeleteOrderedProductListUseCase
import aop.fastcampus.part5.chapter02.domain.product.GetOrderedProductListUseCase
import aop.fastcampus.part5.chapter02.domain.product.GetProductItemUseCase
import aop.fastcampus.part5.chapter02.domain.product.GetProductListUseCase
import aop.fastcampus.part5.chapter02.domain.product.OrderProductItemUseCase
import aop.fastcampus.part5.chapter02.presentation.detail.ProductDetailViewModel
import aop.fastcampus.part5.chapter02.presentation.list.ProductListViewModel
import aop.fastcampus.part5.chapter02.presentation.main.MainViewModel
import aop.fastcampus.part5.chapter02.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ViewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get()) }

    // UseCase
    factory { GetProductListUseCase(get()) }
    factory { GetOrderedProductListUseCase(get()) }
    factory { GetProductItemUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }

    // Repository
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

    single { PreferenceManager(androidContext()) }

    // Database
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

}

