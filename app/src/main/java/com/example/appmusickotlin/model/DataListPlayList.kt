package com.example.appmusickotlin.model

import java.io.Serializable


data class DataListPlayList(
    val title : String,
    var listMusic : MutableList<Song>? = null
) : Serializable