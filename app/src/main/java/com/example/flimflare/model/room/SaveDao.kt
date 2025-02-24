package com.example.flimflare.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM save_table ORDER by id ASC")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM save_table WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>
}