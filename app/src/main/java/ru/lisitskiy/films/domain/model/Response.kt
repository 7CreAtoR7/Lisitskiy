package ru.lisitskiy.films.domain.model

import com.google.gson.annotations.SerializedName


data class Response(

    @SerializedName("films")
    val films: List<Film>

)

