package com.trentzo.moengagenews.storage.sqllite

import android.content.ContentValues
import android.provider.BaseColumns
import com.trentzo.moengagenews.beans.ArticleData
import com.trentzo.moengagenews.beans.OfflineArticleData

object ArticleDbHelper {

    object ArticleEntry : BaseColumns {
        const val TABLE_NAME = "ARTICLE"
        const val COLUMN_NAME_AUTHOR = "author"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_URL = "url"
        const val COLUMN_NAME_URL_TO_IMAGE = "imageUrl"
        const val COLUMN_NAME_PUBLISHED_AT = "publishedAt"
        const val COLUMN_NAME_CONTENT = "content"
        const val COLUMN_NAME_SOURCE_ID = "sourceId"
        const val COLUMN_NAME_SOURCE_NAME = "sourceName"
    }

    @Synchronized
    fun insertArticle(articleData: ArticleData): Long? {

        val values = ContentValues().apply {
            put(ArticleEntry.COLUMN_NAME_AUTHOR, articleData.author)
            put(ArticleEntry.COLUMN_NAME_CONTENT, articleData.content)
            put(ArticleEntry.COLUMN_NAME_DESCRIPTION, articleData.description)
            put(ArticleEntry.COLUMN_NAME_PUBLISHED_AT, articleData.publishedAt)
            put(ArticleEntry.COLUMN_NAME_SOURCE_ID, articleData.source?.id)
            put(ArticleEntry.COLUMN_NAME_SOURCE_NAME, articleData.source?.name)
            put(ArticleEntry.COLUMN_NAME_TITLE, articleData.title)
            put(ArticleEntry.COLUMN_NAME_URL, articleData.url)
            put(ArticleEntry.COLUMN_NAME_URL_TO_IMAGE, articleData.urlToImage)

        }

        return MoEngageSQLiteOpenHelper.sqLiteDatabase?.insert(
            ArticleEntry.TABLE_NAME,
            null, values
        )
    }
    @Synchronized
    fun insertArticle(articleData: OfflineArticleData): Long? {

        val values = ContentValues().apply {
            put(ArticleEntry.COLUMN_NAME_AUTHOR, articleData.author)
            put(ArticleEntry.COLUMN_NAME_CONTENT, articleData.content)
            put(ArticleEntry.COLUMN_NAME_DESCRIPTION, articleData.description)
            put(ArticleEntry.COLUMN_NAME_PUBLISHED_AT, articleData.publishedAt)
            put(ArticleEntry.COLUMN_NAME_SOURCE_ID, articleData.sourceId)
            put(ArticleEntry.COLUMN_NAME_SOURCE_NAME, articleData.sourceName)
            put(ArticleEntry.COLUMN_NAME_TITLE, articleData.title)
            put(ArticleEntry.COLUMN_NAME_URL, articleData.url)
            put(ArticleEntry.COLUMN_NAME_URL_TO_IMAGE, articleData.urlToImage)

        }

        return MoEngageSQLiteOpenHelper.sqLiteDatabase?.insert(
            ArticleEntry.TABLE_NAME,
            null, values
        )
    }

    @Synchronized
    fun truncateEcgTable() {
        MoEngageSQLiteOpenHelper.sqLiteDatabase?.execSQL("DELETE FROM ${ArticleEntry.TABLE_NAME}")
    }

    /**
     * START: DATABASE VERSION UPGRADE
     * */
    @Synchronized
    fun getOfflineArticles(): ArrayList<OfflineArticleData> {

        val projection = arrayOf(
            ArticleEntry.COLUMN_NAME_AUTHOR,
            ArticleEntry.COLUMN_NAME_TITLE,
            ArticleEntry.COLUMN_NAME_DESCRIPTION,
            ArticleEntry.COLUMN_NAME_URL,
            ArticleEntry.COLUMN_NAME_URL_TO_IMAGE,
            ArticleEntry.COLUMN_NAME_PUBLISHED_AT,
            ArticleEntry.COLUMN_NAME_CONTENT,
            ArticleEntry.COLUMN_NAME_SOURCE_ID,
            ArticleEntry.COLUMN_NAME_SOURCE_NAME


        )

        val cursor = MoEngageSQLiteOpenHelper.sqLiteDatabase?.query(
            ArticleEntry.TABLE_NAME,   // The table to query
            projection,            // The array of columns to return (pass null to get all)
            null,         // The columns for the WHERE clause
            null,      // The values for the WHERE clause
            null,         // don't group the rows
            null,          // don't filter by row groups
            null          // The sort order
        )

        val articleDataList = ArrayList<OfflineArticleData>()

        with(cursor) {
            while (this!!.moveToNext()) {
                val articleData = OfflineArticleData(
                    getString(0),
                    getString(1),
                    getString(2),
                    getString(3),
                    getString(4),
                    getString(5),
                    getString(6),
                    getString(7),
                    getString(8)
                )
                articleDataList.add(articleData)
            }
        }
        cursor?.close()
        return articleDataList
    }


}

