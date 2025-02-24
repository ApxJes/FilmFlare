package com.example.flimflare.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flimflare.model.movie.Result

@Entity(tableName = "save_table")
data class SaveEntity(

    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "movie_title") val movieTitle: String?,
    @ColumnInfo(name = "movie_poster") val moviePoster: String?,
    @ColumnInfo(name = "movie_result") val movieResult: Result?

)