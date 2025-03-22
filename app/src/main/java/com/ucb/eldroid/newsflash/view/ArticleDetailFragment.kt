package com.ucb.eldroid.newsflash.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ucb.eldroid.newsflash.R


class ArticleDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_detail, container, false)

        val titleTextView: TextView = view.findViewById(R.id.detailTitle)
        val desTextView: TextView = view.findViewById(R.id.detailDescription)
        val imageView: ImageView = view.findViewById(R.id.detailImage)

        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val imageRes = arguments?.getInt("imageRes")


        titleTextView.text = title
        desTextView.text = description
        imageView.setImageResource(imageRes ?: R.drawable.article_image)

        return view
    }

}