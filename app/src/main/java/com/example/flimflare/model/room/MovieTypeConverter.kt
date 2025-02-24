package com.example.flimflare.model.room

import androidx.room.TypeConverter
import com.example.flimflare.model.movie.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(resultString: String): Result {
        val objectType = object : TypeToken<Result>() {}.type
        return gson.fromJson(resultString, objectType)
    }

}