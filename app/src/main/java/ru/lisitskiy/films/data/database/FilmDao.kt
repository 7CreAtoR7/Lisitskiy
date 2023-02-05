package ru.lisitskiy.films.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {

    // добавление фильма в таблицу избранного
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addFavouriteFilmItem(favouriteFilmItemDbModel: FavouriteFilmItemDbModel)

    @Query("SELECT * FROM allf")
    fun getFilmList(): LiveData<List<FilmItemDbModel>>

    @Query("SELECT * FROM allf WHERE favourite=1")
    fun getFavouriteFilmsList(): LiveData<List<FilmItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmListInDb(listFilmItemDbModel: List<FilmItemDbModel>)

    // добавление фильма в таблицу избранного
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilmItem(filmItemDbModel: FilmItemDbModel)

    // удаление избранного из таблицы// тут в запросе id или filmID
//    @Query("DELETE FROM favourite_films2 WHERE filmID=:filmItemId")
//    suspend fun deleteFilmItem(filmItemId: Long): Int
}