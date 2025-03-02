package com.example.flimflare.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 2, exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(MovieTypeConverter::class, TvShowTypeConverter::class)
abstract class SaveDatabase: RoomDatabase() {

    abstract fun saveDao(): SaveDao
}