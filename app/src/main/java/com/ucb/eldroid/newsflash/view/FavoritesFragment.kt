package com.ucb.eldroid.newsflash.view

import android.annotation.SuppressLint
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

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: ArticlesViewModel
    private val favoriteArticles = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)

        recyclerView = view.findViewById(R.id.articles_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]

        adapter = ArticleRecyclerViewAdapter(favoriteArticles, { selectedArticle ->
            openArticleDetailFragment(selectedArticle)
        }, { article ->
            showRemoveConfirmationDialog(article)
        })

        recyclerView.adapter = adapter

        viewModel.getArticles().observe(viewLifecycleOwner) { articles ->
            loadFavorites(articles)
        }

        viewModel.loadArticles()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadFavorites(articles: List<Article>) {
        val savedFavorites = sharedPreferences.getStringSet("favorite_articles", emptySet()) ?: emptySet()
        favoriteArticles.clear()

        for (article in articles) {
            if (savedFavorites.contains(article.id.toString())) {
                article.isFavorite = true
                favoriteArticles.add(article)
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun showRemoveConfirmationDialog(article: Article) {
        val dialog = ConfirmRemoveDialogFragment(article.title) {
            removeFromFavorites(article)
        }
        dialog.show(parentFragmentManager, "ConfirmRemoveDialog")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeFromFavorites(article: Article) {
        article.isFavorite = false
        favoriteArticles.remove(article)
        saveFavorites()
        adapter.notifyDataSetChanged()
    }

    private fun saveFavorites() {
        val editor = sharedPreferences.edit()
        val ids = favoriteArticles.map { it.id.toString() }.toSet()
        editor.putStringSet("favorite_articles", ids)
        editor.apply()
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
}


