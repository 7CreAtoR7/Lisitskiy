package ru.lisitskiy.films.domain.model

import com.google.gson.annotations.SerializedName


data class Genre(

    @SerializedName("genre")
    val genre: String

)