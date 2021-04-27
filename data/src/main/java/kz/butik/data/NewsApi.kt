package kz.butik.data

import kz.butik.data.api.NetResult
import kz.butik.data.responses.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


const val COUNTRY_US = "us"
const val QUERY_APPLE = "Apple"

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlinesNewsInUS(
        @Query("country") country: String = COUNTRY_US,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ) : NetResult<NewsResponse>

    @GET("everything")
    suspend fun getAllAppleNews(
        @Query("q") query: String = QUERY_APPLE,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ) : NetResult<NewsResponse>
}