package com.example.flimflare.model.room

import android.graphics.Movie
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(MovieTypeConverter::class)
abstract class SaveDatabase: RoomDatabase() {

    abstract fun saveDao(): SaveDao
}