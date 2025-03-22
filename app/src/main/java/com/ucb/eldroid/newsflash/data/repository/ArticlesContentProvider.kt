package com.ucb.eldroid.newsflash.data.repository

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.ucb.eldroid.newsflash.R

class ArticlesContentProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "com.ucb.eldroid.newsflash.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/articles")

        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE = "image"

        // Sample Data
        private val articles = listOf(
            arrayOf(1, "Breaking News: Market Crash", R.drawable.market_crash_image),
            arrayOf(2, "Sports Update: Champions League", R.drawable.champ_league_image),
            arrayOf(3, "Technology: New AI Breakthrough", R.drawable.new_ai_image),
            arrayOf(4, "Fire Alert: Fire Happening at Apas, Lahug Cebu", R.drawable.fire_image)
        )
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = MatrixCursor(arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE))
        for (article in articles) {
            cursor.addRow(article)
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Insert not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Delete not supported")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Update not supported")
    }

    override fun getType(uri: Uri): String? = null
}
