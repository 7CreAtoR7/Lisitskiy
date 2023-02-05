package ru.lisitskiy.films.domain.usecases

import ru.lisitskiy.films.domain.repository.FilmRepository
import ru.lisitskiy.films.domain.model.Film

class AddFilmListInDbUseCase (
    private val filmRepository: FilmRepository
) {

    suspend fun addFilmListInDb(listFilmItem: List<Film>) {
        filmRepository.addFilmListInDb(listFilmItem)
    }
}