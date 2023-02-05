package ru.lisitskiy.films.domain.usecases

import androidx.lifecycle.LiveData
import ru.lisitskiy.films.domain.repository.FilmRepository
import ru.lisitskiy.films.domain.model.Film

class GetFilmListUseCase(
    private val repository: FilmRepository
) {

    fun getFilmList(): LiveData<List<Film>>{
        return repository.getFilmList()
    }

}