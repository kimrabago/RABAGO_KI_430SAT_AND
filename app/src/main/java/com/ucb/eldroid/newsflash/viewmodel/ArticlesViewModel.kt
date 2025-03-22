package com.ucb.eldroid.newsflash.viewmodel

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ucb.eldroid.newsflash.data.repository.ArticlesContentProvider
import com.ucb.eldroid.newsflash.utils.NotificationHelper
import com.ucb.eldroid.newsflash.data.model.Article

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val articlesLiveData = MutableLiveData<List<Article>>()

    fun getArticles(): LiveData<List<Article>> = articlesLiveData

    fun loadArticles() {
        val cursor: Cursor? = getApplication<Application>().contentResolver.query(
            ArticlesContentProvider.CONTENT_URI, null, null, null, null
        )
        val articles = mutableListOf<Article>()

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(ArticlesContentProvider.COLUMN_ID))
                val title = it.getString(it.getColumnIndexOrThrow(ArticlesContentProvider.COLUMN_TITLE))
                val imageRes = it.getInt(it.getColumnIndexOrThrow(ArticlesContentProvider.COLUMN_IMAGE))
                articles.add(Article(id, title, imageRes))
            }
        }

        articlesLiveData.postValue(articles)
    }
}