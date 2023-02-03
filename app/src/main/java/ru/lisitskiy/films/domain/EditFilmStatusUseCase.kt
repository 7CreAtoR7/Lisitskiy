package ru.lisitskiy.films.domain

class EditFilmStatusUseCase (
    private val repository: FilmRepository
) {
    // TODO(внедрение зависимостей через dagger2)
    operator fun invoke(name: String) = repository.editFilmStatus(name)
}
