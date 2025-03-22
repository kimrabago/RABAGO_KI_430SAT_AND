package com.ucb.eldroid.newsflash.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ucb.eldroid.newsflash.R
import com.ucb.eldroid.newsflash.data.model.Article

class ArticleRecyclerViewAdapter(
    private var articles: MutableList<Article>,
    private val onArticleClick: (Article) -> Unit,
    private val onFavoriteClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.articleHeadline)
        val imageView: ImageView = view.findViewById(R.id.articleImage)
        val favoriteButton: ImageButton = view.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.imageView.setImageResource(article.imageRes)

        // Update favorite button icon based on isFavorite status
        holder.favoriteButton.setImageResource(
            if (article.isFavorite) R.drawable.favorite_color else R.drawable.unfavorite
        )

        holder.itemView.setOnClickListener {
            onArticleClick(article)
        }

        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(article)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = articles.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }
}
