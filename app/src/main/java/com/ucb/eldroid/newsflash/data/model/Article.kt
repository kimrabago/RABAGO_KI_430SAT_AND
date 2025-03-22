package com.ucb.eldroid.newsflash.data.model

data class Article(
    val id: Int,
    val title: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)