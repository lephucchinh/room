package com.example.appmusickotlin.util.callBack

import com.example.appmusickotlin.model.DataListPlayList
import com.example.appmusickotlin.model.Song

interface OnEditButtonClickListener {
    fun onEditButtonClick(song: Song)
    fun onDeleteButtonClick(song: Song, position: Int)
}


interface PlaylistAddedListener {
    fun onPlaylistAdded(album: DataListPlayList)
}


interface OnItemClickListener {
    fun onItemClick(position: Int)

}

interface OnMusicClickListener {
    fun onItemClick(song: Song)
}
