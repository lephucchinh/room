package com.example.appmusickotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmusickotlin.db.entity.PlaylistEntity
import com.example.appmusickotlin.db.entity.UserEntity

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists WHERE userId = :userId")
    suspend fun getPlaylistsByUserId(userId: Long): List<PlaylistEntity>

    @Query("SELECT * FROM playlists")
    fun getAllUsers(): LiveData<List<PlaylistEntity>>

    // Các phương thức khác như update, delete, query tùy vào nhu cầu
}