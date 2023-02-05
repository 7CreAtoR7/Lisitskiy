package ru.lisitskiy.films.presentation.viewmodels


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import ru.lisitskiy.films.data.network.ApiFactory
import ru.lisitskiy.films.data.repository.FilmRepositoryImpl
import ru.lisitskiy.films.domain.model.Film
import ru.lisitskiy.films.domain.usecases.*

import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build

class FilmViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmRepositoryImpl(application)

    private val getFilmListUseCase = GetFilmListUseCase(repository)

    // добавление в таблицу с новым статусом favourite
    private val addFilmItemUseCase = AddFilmItemUseCase(repository)

    // 20 фильмов в бд
    private val addFilmListInDbUseCase = AddFilmListInDbUseCase(repository)

    val filmList = getFilmListUseCase.getFilmList()

    val isInternetConnection: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()

    var PAGE_PAGINATION = 1
    val MAX_PAGE_COUNT = 5

    init {
        getInternetInfo()
    }

    fun getDataFromApi(page: Int) {
        ApiFactory.getApiService().getTopFilms(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val newFilms = it.films
                    // добавляем 20 предметов в бд
                    addNewTwentyFilmsInDb(newFilms)
                    // теперь подписываемся на livedata db
                },
                { Log.d("FilmViewModel", "getDataFromApi: ${it.message}") }
            )
    }

    fun changeEnableState(film: Film) { // изменение только статуса
        viewModelScope.launch {
            val newItem = film.copy(favourite = !film.favourite)
            // в копию устанавливаем статус противоположный
            addFilmItemUseCase.addFilmItem(newItem)
        }
    }

    // 20 фильмов в бд
    fun addNewTwentyFilmsInDb(listFilmItem: List<Film>) {
        viewModelScope.launch {
            addFilmListInDbUseCase.addFilmListInDb(listFilmItem)
        }
    }


    fun getInternetInfo() = viewModelScope.launch {
        safeCall()
    }


    private suspend fun safeCall() {
        try {
            if (!hasInternetConnection()) {
                isInternetConnection.postValue(false)
            } else {
                isInternetConnection.postValue(true)
            }
        } catch (t: Throwable) {
            isError.postValue(true)
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else { // версия до API 23
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}