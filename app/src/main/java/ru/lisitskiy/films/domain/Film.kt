package ru.lisitskiy.films.domain

data class Film(
    val name: String,
    val posterUrl: String,
    val year: Int,
    val description: String,
    val countries: String,
    val genres: String,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}