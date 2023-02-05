package ru.lisitskiy.films.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.lisitskiy.films.domain.model.Country
import ru.lisitskiy.films.domain.model.Genre

@Entity(tableName = "allf")
data class FilmItemDbModel(

    @PrimaryKey
    val filmID: Long,

    val nameRu: String,

    val year: String?,

    @TypeConverters(ConverterCountry::class)
    val countries: List<Country>?,

    @TypeConverters(ConverterGenre::class)
    val genres: List<Genre>?,

    val posterURL: String?,

    val favourite: Boolean = false

)
