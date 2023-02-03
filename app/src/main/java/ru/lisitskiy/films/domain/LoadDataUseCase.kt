package ru.lisitskiy.films.domain

class LoadDataUseCase (
    private val repository: FilmRepository
) {
    // TODO(внедрение зависимостей через dagger2)
    suspend operator fun invoke() = repository.loadData()
}
