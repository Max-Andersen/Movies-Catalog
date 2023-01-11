package com.example.moviecatalog.network


import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviecatalog.network.Auth.AuthApi
import com.example.moviecatalog.network.FavoriteMovies.FavoriteMoviesApi
import com.example.moviecatalog.network.Movie.MovieApi
import com.example.moviecatalog.network.Review.ReviewApi
import com.example.moviecatalog.network.User.UserApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.content.Context
import com.example.moviecatalog.MainApplication


object Network {
    private const val BASE_URL = "https://react-midterm.kreosoft.space/api/"

    //private const val BASE_URL = "https://6e71-5-165-213-157.in.ngrok.io/api/"

    var masterKey = MasterKey.Builder(MainApplication.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    var sharedPreferences = EncryptedSharedPreferences.create(
        MainApplication.applicationContext(),
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun updateToken(newToken: String){
        sharedPreferences.edit().putString("bearer_token", newToken).apply()
        token = newToken
    }

    @JvmName("getToken1")
    fun getToken(): String {
        return if (token == null){
            ""
        } else{
            token as String
        }

    }

    private var token = sharedPreferences.getString("bearer_token", "")

    var userId = ""

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(): OkHttpClient{
        val client =  OkHttpClient.Builder().apply{
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(TokenInterceptor())
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

    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)

    fun getFavoriteMoviesApi(): FavoriteMoviesApi = retrofit.create(FavoriteMoviesApi::class.java)

    fun getReviewApi(): ReviewApi = retrofit.create(ReviewApi::class.java)

}