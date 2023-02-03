package ru.lisitskiy.films.domain

import androidx.lifecycle.LiveData

interface FilmRepository {

    fun getFilmInfoList(): LiveData<List<Film>>

    fun getFilmInfo(name: String): LiveData<Film>

    fun editFilmStatus(name: String)

    suspend fun loadData()
}