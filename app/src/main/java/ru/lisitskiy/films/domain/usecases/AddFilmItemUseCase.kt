package ru.lisitskiy.films.domain.usecases

import ru.lisitskiy.films.domain.repository.FilmRepository
import ru.lisitskiy.films.domain.model.Film

class AddFilmItemUseCase (
    private val filmRepository: FilmRepository
    ) {

    suspend fun addFilmItem(filmItem: Film) {
        filmRepository.addFilmItem(filmItem)
    }
}