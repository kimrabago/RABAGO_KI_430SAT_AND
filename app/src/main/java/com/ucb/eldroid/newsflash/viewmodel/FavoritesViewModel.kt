package com.ucb.eldroid.newsflash.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ucb.eldroid.newsflash.data.model.Article

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    private val _favoriteArticles = MutableLiveData<List<Article>>()
    val favoriteArticles: LiveData<List<Article>> get() = _favoriteArticles

    fun loadFavorites(articles: List<Article>) {
        val savedFavorites = sharedPreferences.getStringSet("favorite_articles", emptySet()) ?: emptySet()

        val favoritesList = articles.filter { savedFavorites.contains(it.id.toString()) }
            .onEach { it.isFavorite = true }

        _favoriteArticles.postValue(favoritesList)
    }

    fun removeFromFavorites(article: Article) {
        val currentFavorites = _favoriteArticles.value?.toMutableList() ?: mutableListOf()
        currentFavorites.remove(article)
        article.isFavorite = false

        _favoriteArticles.postValue(currentFavorites)
        saveFavorites(currentFavorites)
    }

    private fun saveFavorites(favorites: List<Article>) {
        val editor = sharedPreferences.edit()
        val ids = favorites.map { it.id.toString() }.toSet()
        editor.putStringSet("favorite_articles", ids)
        editor.apply()
    }
}
