package ru.lisitskiy.films.domain

class GetFilmInfoUseCase (
    private val repository: FilmRepository
) {
    // TODO(внедрение зависимостей через dagger2)
    operator fun invoke(name: String) = repository.getFilmInfo(name)
}
