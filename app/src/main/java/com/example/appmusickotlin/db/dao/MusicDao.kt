package com.example.appmusickotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.MusicEntity

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(music: MusicEntity)

    @Query("SELECT * FROM musics WHERE playlistId = :playlistId")
    suspend fun getMusicsByPlaylistId(playlistId: Long): List<MusicEntity>

    // Các phương thức khác như update, delete, query tùy vào nhu cầu
}