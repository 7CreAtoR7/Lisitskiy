package ru.lisitskiy.films.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FilmItemDbModel::class],
    version = 8
)
@TypeConverters(ConverterCountry::class, ConverterGenre::class)
// преобразование списка с годом произвоства и списка стан
abstract class FilmDataBase : RoomDatabase() {

    abstract fun getFilmDao(): FilmDao

    companion object {
        private var INSTANCE: FilmDataBase? = null
        private val LOCK = Any()
        private const val DB_NAME = "films_db.db"

        fun getInstance(application: Application): FilmDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    FilmDataBase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}