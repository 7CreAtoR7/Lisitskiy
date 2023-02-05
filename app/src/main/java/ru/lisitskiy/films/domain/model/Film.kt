package ru.lisitskiy.films.domain.model

import com.google.gson.annotations.SerializedName

data class Film(

    @SerializedName("filmId")
    val filmID: Long,

    @SerializedName("nameRu")
    val nameRu: String,

    @SerializedName("year")
    val year: String?,

    @SerializedName("countries")
    val countries: List<Country>?,

    @SerializedName("genres")
    val genres: List<Genre>?,

    @SerializedName("posterUrl")
    val posterURL: String?,

    val favourite: Boolean = false

    )
