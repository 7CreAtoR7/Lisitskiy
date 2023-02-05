package ru.lisitskiy.films.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.lisitskiy.films.domain.model.Country
import ru.lisitskiy.films.domain.model.Genre
import java.lang.reflect.Type

class ConverterGenre {

    private val gson = Gson()

    @TypeConverter
    fun listToString(data: List<Genre>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToList(genre: String): List<Genre>? {
        val listType = object : TypeToken<List<Genre>?>() {}.type
        return gson.fromJson<List<Genre>?>(genre, listType)
    }

}