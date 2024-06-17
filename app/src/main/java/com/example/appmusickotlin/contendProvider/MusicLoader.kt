package com.example.appmusickotlin.contendProvider

import android.content.Context
import android.provider.MediaStore
import com.example.appmusickotlin.model.Song

class MusicLoader(private val context: Context) {

    fun getAllMusic(): MutableList<Song> {
        val musicUriList = mutableListOf<Song>()

        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST
        )

        // Thêm điều kiện cho câu truy vấn
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf("1000") // Thời lượng >= 1000ms (1 giây)

        val musicCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            selection,
            selectionArgs,
            null
        )

        musicCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val artist = cursor.getString(artistColumn)

                val song = Song(id, title, duration, albumId, artist)
                musicUriList.add(song)
            }
        }

        // Sắp xếp danh sách theo thứ tự alphabetic của name
        musicUriList.sortBy { it.name }

        return musicUriList
    }
}