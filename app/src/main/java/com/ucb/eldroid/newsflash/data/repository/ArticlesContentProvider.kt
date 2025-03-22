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
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(): Boolean {
        return true
    }

    private fun getArticlesList(): List<Array<Any>> {
        val context = context ?: return emptyList()

        return listOf(
            arrayOf(
                1,
                context.getString(R.string.title_market_crash),
                R.drawable.market_crash_image,
                context.getString(R.string.desc_market_crash)
            ),
            arrayOf(
                2,
                context.getString(R.string.title_champions_league),
                R.drawable.champ_league_image,
                context.getString(R.string.desc_champions_league)
            ),
            arrayOf(
                3,
                context.getString(R.string.title_ai_breakthrough),
                R.drawable.new_ai_image,
                context.getString(R.string.desc_ai_breakthrough)
            ),
            arrayOf(
                4,
                context.getString(R.string.title_fire_alert),
                R.drawable.fire_image,
                context.getString(R.string.desc_fire_alert)
            )
        )
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = MatrixCursor(arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE, COLUMN_DESCRIPTION))

        for (article in getArticlesList()) {
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
