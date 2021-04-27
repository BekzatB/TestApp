package kz.butik.data.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kz.butik.data.BuildConfig.NEWS_API
import kz.butik.data.NewsApi
import kz.butik.data.api.CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {

    single<Gson> { GsonBuilder().create() }
    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single<NewsApi> {

        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl(NEWS_API)
            .addCallAdapterFactory(CallAdapterFactory())
            .addConverterFactory(get())
            .build()

        return@single retrofit.create(NewsApi::class.java)
    }

    single { getHttpClient(get(), get()) }

    single { getLoggingInterceptor() }

    single { getApiKeyInterceptor() }

}

private fun getHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiKeyInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun getApiKeyInterceptor(): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter(
                "apiKey",
                "942fde866f4e4e1091c6bfeca2fe93ad"
            )
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}


private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message -> Log.d("OkHttp", message) }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}