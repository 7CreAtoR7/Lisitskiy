package ru.lisitskiy.films.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.lisitskiy.films.domain.model.Country


class ConverterCountry {
    private val gson = Gson()

    @TypeConverter
    fun listToString(data: List<Country>?): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToList(countries: String): List<Country>? {
        val listType = object : TypeToken<List<Country>?>() {}.type
        return gson.fromJson<List<Country>?>(countries, listType)
    }
}