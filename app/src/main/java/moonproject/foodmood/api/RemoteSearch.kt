package moonproject.foodmood.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RemoteSearch {

    private const val BASE_URL = "https://fitaudit.ru/json/findfood/"
    private var retrofit: Retrofit? = null

    fun getService(): SearchService {

        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(100   , TimeUnit.SECONDS)
                .writeTimeout(60  , TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create()
    }

}