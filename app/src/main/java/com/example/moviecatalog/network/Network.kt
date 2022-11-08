package com.example.moviecatalog.network


import com.example.moviecatalog.network.Auth.AuthApi
import com.example.moviecatalog.network.Movie.MovieApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

    private const val BASE_URL = "https://react-midterm.kreosoft.space/api/"
    var token = ""

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(): OkHttpClient{
        val client =  OkHttpClient.Builder().apply{
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)

            // Authenticator не понадобится

            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel ))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)

    fun getMovieApi(): MovieApi = retrofit.create(MovieApi::class.java)

}