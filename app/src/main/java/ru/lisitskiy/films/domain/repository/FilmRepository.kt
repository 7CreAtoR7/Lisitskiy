package ru.lisitskiy.films.domain.repository

import androidx.lifecycle.LiveData
import ru.lisitskiy.films.domain.model.Film

interface FilmRepository {

    fun getFavouriteFilmsList(): LiveData<List<Film>>

    suspend fun addFilmListInDb(listFilm: List<Film>)

    suspend fun addFilmItem(filmItem: Film)

    suspend fun deleteFilmItem(filmId: Long)

    fun getFilmList(): LiveData<List<Film>>

}
