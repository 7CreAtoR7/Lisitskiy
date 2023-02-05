package ru.lisitskiy.films.data.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiFactory() {

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        private var INSTANCE: ApiService? = null
        private val LOCK = Any()


        fun getApiService(): ApiService {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()

                INSTANCE = retrofit.create()
                return retrofit.create()
            }
        }
    }
}
