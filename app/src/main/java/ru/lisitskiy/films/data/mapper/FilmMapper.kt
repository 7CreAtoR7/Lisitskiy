package ru.lisitskiy.films.data.mapper

import ru.lisitskiy.films.data.database.FilmItemDbModel
import ru.lisitskiy.films.domain.model.Film

class FilmMapper {

    fun mapEntityToDbModel(film: Film): FilmItemDbModel {
        return FilmItemDbModel(
            filmID = film.filmID,
            nameRu = film.nameRu,
            year = film.year,
            countries = film.countries, // List<Country>?,
            genres = film.genres, // List<Genre>?,
            posterURL = film.posterURL,
            favourite = film.favourite
        )
    }

    fun mapListDbModelToListEntity(list: List<FilmItemDbModel>): List<Film> {
        return list.map {
            mapDbModelToEntity(it)
        }
    }

    fun mapDbModelToEntity(filmItemDbModel: FilmItemDbModel): Film {
        return Film(
            filmID = filmItemDbModel.filmID,
            nameRu = filmItemDbModel.nameRu,
            year = filmItemDbModel.year,
            countries = filmItemDbModel.countries, // List<Country>?,
            genres = filmItemDbModel.genres, // List<Genre>?,
            posterURL = filmItemDbModel.posterURL,
            favourite = filmItemDbModel.favourite
        )
    }


    fun mapEntityListToDbModelList(listFilmItem: List<Film>): List<FilmItemDbModel> {
        val listFilmItemDbModel = mutableListOf<FilmItemDbModel>()
        for (elem in listFilmItem) {
            val DBelem = mapEntityToDbModel(elem)
            listFilmItemDbModel.add(DBelem)
        }
        return listFilmItemDbModel.toList()
    }

}