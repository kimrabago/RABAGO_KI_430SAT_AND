package com.ucb.eldroid.newsflash.view

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
import com.ucb.eldroid.newsflash.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerViewAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var articlesViewModel: ArticlesViewModel
    private val favoriteArticles = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById(R.id.articles_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        favoritesViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[FavoritesViewModel::class.java]

        articlesViewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]

        adapter = ArticleRecyclerViewAdapter(favoriteArticles, { selectedArticle ->
            openArticleDetailFragment(selectedArticle)
        }, { article ->
            showRemoveConfirmationDialog(article)
        })

        recyclerView.adapter = adapter

        articlesViewModel.getArticles().observe(viewLifecycleOwner) { articles ->
            if (articles.isNotEmpty()) {
                favoritesViewModel.loadFavorites(articles)
            }
        }

        favoritesViewModel.favoriteArticles.observe(viewLifecycleOwner) { updatedFavorites ->
            favoriteArticles.clear()
            favoriteArticles.addAll(updatedFavorites)
            adapter.notifyDataSetChanged()
        }

        articlesViewModel.loadArticles()

        return view
    }

    private fun showRemoveConfirmationDialog(article: Article) {
        val dialog = ConfirmRemoveDialogFragment(article.title) {
            favoritesViewModel.removeFromFavorites(article)
        }
        dialog.show(parentFragmentManager, "ConfirmRemoveDialog")
    }

    private fun openArticleDetailFragment(article: Article) {
        val fragment = ArticleDetailFragment().apply {
            arguments = Bundle().apply {
                putString("title", article.title)
                putInt("imageRes", article.imageRes)
                putString("description", article.description)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, fragment)
            .addToBackStack(null)
            .commit()
    }
}
