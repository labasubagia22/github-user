package com.example.githubsearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {

    // config
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val TOKEN = "2ce6133b8bd727ae7367c63f23a5c3283f23738c"
    }

    private fun getHttpClient(): OkHttpClient {

        // log interceptor to see in logcat
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // make client for retrofit
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // add github api token
                val originalRequest = chain.request()
                val request = originalRequest.newBuilder()
                    .addHeader("Authorization", "token $TOKEN")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    // retrofit instance
    private fun getNetwork() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // create api with API Interface
    fun api(): ApiInterface = getNetwork().create(ApiInterface::class.java)
}