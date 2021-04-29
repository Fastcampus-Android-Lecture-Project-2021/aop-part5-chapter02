package aop.fastcampus.part5.chapter02.data.network

import aop.fastcampus.part5.chapter02.data.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>

}
