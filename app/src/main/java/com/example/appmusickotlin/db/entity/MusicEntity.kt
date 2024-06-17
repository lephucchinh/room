package com.example.appmusickotlin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val playlistId: Long // Foreign key reference to PlaylistEntity
)