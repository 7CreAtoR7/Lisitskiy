package ru.lisitskiy.films.domain.usecases

import ru.lisitskiy.films.domain.repository.FilmRepository

class DeleteFilmItemUseCase(
    private val repository: FilmRepository
    ) {

    suspend fun deleteFilmItem(filmId: Long) {
        repository.deleteFilmItem(filmId)
    }

}