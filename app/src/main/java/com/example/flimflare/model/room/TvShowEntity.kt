package com.example.flimflare.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flimflare.model.tvShow.Result

@Entity(tableName = "show_table")
data class TvShowEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "tv_show_title") val showTitle: String?,
    @ColumnInfo(name = "tv_show_poster") val showPoster: String?,
    @ColumnInfo(name = "tv_show_result") val showResult: Result?,
)