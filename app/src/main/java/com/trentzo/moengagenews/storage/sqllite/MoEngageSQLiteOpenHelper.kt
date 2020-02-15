package com.trentzo.moengagenews.storage.sqllite

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.trentzo.moengagenews.beans.OfflineArticleData

class MoEngageSQLiteOpenHelper(applicationContext: Context)
    : SQLiteOpenHelper(applicationContext,
        StorageConstants.DATABASE_NAME,
        null,
        StorageConstants.DATABASE_VERSION) {

    init {
        if (applicationContext is Activity) {
            throw IllegalArgumentException(StorageConstants.MSG_ILLEGAL_ARGS)
        } else if (sqLiteDatabase == null) {
            sqLiteDatabase = writableDatabase
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(StorageConstants.SQL_CREATE_ARTICLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (sqLiteDatabase == null)
            sqLiteDatabase = db

        val articleList: ArrayList<OfflineArticleData>? = if (newVersion > oldVersion) ArticleDbHelper.getOfflineArticles() else null

        db.execSQL(StorageConstants.SQL_DELETE_CAPTURED_ENTRIES)
        onCreate(db)

        if (articleList != null && articleList.size > 0){
            for(i in 0.. (articleList.size-1)){
                ArticleDbHelper.insertArticle(articleList.get(i))
            }
        }


    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        var sqLiteDatabase: SQLiteDatabase? = null
    }

    fun destroy() {
        sqLiteDatabase?.close()
    }
}