package ru.lisitskiy.films.domain.usecases

import androidx.lifecycle.LiveData
import ru.lisitskiy.films.domain.repository.FilmRepository
import ru.lisitskiy.films.domain.model.Film

class GetFavouriteFilmsUseCase(
    private val repository: FilmRepository
) {

    fun getFavouriteFilmsList(): LiveData<List<Film>> {
        return repository.getFavouriteFilmsList()
    }

}