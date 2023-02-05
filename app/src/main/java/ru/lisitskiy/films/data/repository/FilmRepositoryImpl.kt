package ru.lisitskiy.films.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.lisitskiy.films.data.mapper.FilmMapper
import ru.lisitskiy.films.data.database.FilmDataBase
import ru.lisitskiy.films.domain.repository.FilmRepository
import ru.lisitskiy.films.domain.model.Film

class FilmRepositoryImpl(
    application: Application
) : FilmRepository {

    private val filmListDao = FilmDataBase.getInstance(application).getFilmDao()
    private val mapper = FilmMapper()

    // лд всех фильмов, подписываемся в активити
    override fun getFilmList(): LiveData<List<Film>> {
        return Transformations.map(filmListDao.getFilmList()) {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    // получение лд избранного
    override fun getFavouriteFilmsList(): LiveData<List<Film>> {
        return Transformations.map(filmListDao.getFavouriteFilmsList()) {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    // добавление 20 фильмов в бд
    override suspend fun addFilmListInDb(listFilm: List<Film>) {
        filmListDao.addFilmListInDb(mapper.mapEntityListToDbModelList(listFilm))
    }

    override suspend fun addFilmItem(filmItem: Film) {
        filmListDao.addFilmItem(mapper.mapEntityToDbModel(filmItem))
    }

    override suspend fun deleteFilmItem(filmId: Long) {
        TODO("Not yet implemented")
    }


}
