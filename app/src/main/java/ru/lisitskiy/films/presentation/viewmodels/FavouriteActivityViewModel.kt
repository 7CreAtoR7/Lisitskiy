package ru.lisitskiy.films.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import ru.lisitskiy.films.data.network.ApiFactory
import ru.lisitskiy.films.data.repository.FilmRepositoryImpl
import ru.lisitskiy.films.domain.model.Film
import ru.lisitskiy.films.domain.usecases.*

class FavouriteActivityViewModel (
    application: Application
) : AndroidViewModel(application) {

    private val repository = FilmRepositoryImpl(application)

    private val getFilmListUseCase = GetFilmListUseCase(repository)
//    private val deleteFilmItemUseCase = DeleteFilmItemUseCase(repository)
//
//    // добавление в табоицу с новым статусом favourite
//    private val addFilmItemUseCase = AddFilmItemUseCase(repository)
//
//    // 20 фильмов в бд
//    private val addFilmListInDbUseCase = AddFilmListInDbUseCase(repository)
//
//    // фильм в избранное
//    private val addFavouriteFilmToDb = AddFavouriteFilmToDb(repository)

    private val getFavouriteFilmsUseCollection = GetFavouriteFilmsUseCase(repository)

    //val filmList = getFilmListUseCase.getFilmList()
    val favouriteFilms = getFavouriteFilmsUseCollection.getFavouriteFilmsList()

    private val _favourites = MutableLiveData<List<Film>>()
    val favourites: LiveData<List<Film>>
        get() = _favourites

//    var PAGE_PAGINATION = 1
//    val MAX_PAGE_COUNT = 1
//
//    fun getDataFromApi(page: Int) {
//        ApiFactory.getApiService().getTopFilms(page)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.d("FilmViewModel", "getDataFromApi: ${it.films}")
//                    val newFilms = it.films
//                    // добавляем 20 предметов в бд
//                    addNewTwentyFilmsInDb(newFilms)
//                    // теперь подписываемся на livedata db
//
//                },
//                { Log.d("FilmViewModel", "getDataFromApi: ${it.message}") }
//            )
//    }

//    fun changeEnableState(film: Film) { // изменение только статуса
//        viewModelScope.launch {
//            val newItem = film.copy(favourite = !film.favourite)
//            // в копию устанавливаем статус противоположный
//            addFilmItemUseCase.addFilmItem(newItem)
//        }
//    }

//    fun addFilmToFavouriteDb(filmItem: Film) {
//        viewModelScope.launch {
//            addFavouriteFilmToDb.addFilmToFavourite(filmItem)
//        }
//    }


//    fun addFilmToDb(filmItem: Film) {
//        viewModelScope.launch {
//            addFilmItemUseCase.addFilmItem(filmItem)
//        }
//    }

    // 20 фильмов в бд
//    fun addNewTwentyFilmsInDb(listFilmItem: List<Film>) {
//        viewModelScope.launch {
//            addFilmListInDbUseCase.addFilmListInDb(listFilmItem)
//        }
//    }
//
//    fun deleteFavouriteFilm(film: Film) {
//        viewModelScope.launch {
//            deleteFilmItemUseCase.deleteFilmItem(film.filmID)
//        }
//    }

}