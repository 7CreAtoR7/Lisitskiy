package ru.lisitskiy.films.domain.model

import com.google.gson.annotations.SerializedName


data class Country (

    @SerializedName("country")
    val country: String

)
