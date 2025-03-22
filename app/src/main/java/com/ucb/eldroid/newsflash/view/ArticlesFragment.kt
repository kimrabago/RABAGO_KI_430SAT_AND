package com.ucb.eldroid.newsflash.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ucb.eldroid.newsflash.R
import com.ucb.eldroid.newsflash.data.model.Article
import com.ucb.eldroid.newsflash.viewmodel.ArticlesViewModel

class ArticlesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerViewAdapter
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val favoriteArticles = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_articles, container, false)

        sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)

        recyclerView = view.findViewById(R.id.articles_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]

        adapter = ArticleRecyclerViewAdapter(mutableListOf(), { selectedArticle ->
            openArticleDetailFragment(selectedArticle)
        }, { article ->
            toggleFavorite(article)
        })

        recyclerView.adapter = adapter

        viewModel.getArticles().observe(viewLifecycleOwner) { articles ->
            loadFavorites(articles)
            adapter.updateData(articles)
        }

        viewModel.loadArticles()

        return view
    }

    private fun openArticleDetailFragment(article: Article) {
        val fragment = ArticleDetailFragment().apply {
            arguments = Bundle().apply {
                putString("title", article.title)
                putInt("imageRes", article.imageRes)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Toggle article's favorite status (add or remove).
    private fun toggleFavorite(article: Article) {
        article.isFavorite = !article.isFavorite
        if (article.isFavorite) {
            favoriteArticles.add(article)
        } else {
            favoriteArticles.remove(article)
        }
        saveFavorites()
    }

    private fun saveFavorites() {
        val editor = sharedPreferences.edit()
        val ids = favoriteArticles.map { it.id.toString() }.toSet()
        editor.putStringSet("favorite_articles", ids)
        editor.apply()
    }

    private fun loadFavorites(articles: List<Article>) {
        val savedFavorites = sharedPreferences.getStringSet("favorite_articles", emptySet()) ?: emptySet()
        favoriteArticles.clear()
        for (article in articles) {
            if (savedFavorites.contains(article.id.toString())) {
                article.isFavorite = true
                favoriteArticles.add(article)
            }
        }
    }
}

