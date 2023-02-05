package ru.lisitskiy.films.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.lisitskiy.films.domain.model.DetailFilm
import ru.lisitskiy.films.domain.model.Response


interface ApiService {

    @Headers("accept: application/json", "X-API-KEY: 71f6d356-ad50-42ff-a336-3b8fa804af09")
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    fun getTopFilms(
        @Query("page") page: Int
    ): Single<Response>

    @Headers("accept: application/json", "X-API-KEY: 71f6d356-ad50-42ff-a336-3b8fa804af09")
    @GET("api/v2.2/films/{id}")
    fun getDetailInfo(@Path("id") id: Int): Single<DetailFilm>

    @Headers("accept: application/json", "X-API-KEY: 71f6d356-ad50-42ff-a336-3b8fa804af09")
    @GET("api/v2.1/films/search-by-keyword")
    fun searchAndGetFilms(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): Single<Response>

}
