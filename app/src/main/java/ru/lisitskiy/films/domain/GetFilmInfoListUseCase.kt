package ru.lisitskiy.films.domain

class GetFilmInfoListUseCase(
    private val repository: FilmRepository
) {
    // TODO(внедрение зависимостей через dagger2)
    operator fun invoke() = repository.getFilmInfoList()
}
