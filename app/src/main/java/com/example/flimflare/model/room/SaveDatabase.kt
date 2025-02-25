package com.example.flimflare.model.room

import android.graphics.Movie
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = true)
@TypeConverters(MovieTypeConverter::class, TvShowTypeConverter::class)
abstract class SaveDatabase: RoomDatabase() {

    abstract fun saveDao(): SaveDao
}