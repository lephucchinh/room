package com.example.appmusickotlin.db.repository

import androidx.lifecycle.LiveData
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.appmusickotlin.db.dao.PlaylistDao
import com.example.appmusickotlin.db.entity.PlaylistEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistRepository(private val playlistDao : PlaylistDao) {
    val allPlaylists : LiveData<List<PlaylistEntity>> = playlistDao.getAllUsers()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @UnstableApi
    suspend fun insert(playlist : PlaylistEntity){
        coroutineScope.launch {
            Log.e("ddddd" , playlist.toString())
            playlistDao.insertPlaylist(playlist)
        }
    }
    suspend fun getPlaylist(userId : Long) : List<PlaylistEntity> {
        return withContext(Dispatchers.IO) {
            playlistDao.getPlaylistsByUserId(userId)
        }
    }
}