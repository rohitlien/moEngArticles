package com.trentzo.moengagenews.storage.sqllite

import android.provider.BaseColumns

object StorageConstants {

    const val MSG_ILLEGAL_ARGS = "The argument should be the application context!"

    const val DATABASE_VERSION = 5
    const val DATABASE_NAME = "BR1000.sqLiteDatabase"
    const val PREFERENCE_KEY = "userData"
    const val IS_ALARM_SET = "isAlermSet"
    val VALUE_NOT_SET: String? = null


    const val SQL_CREATE_ARTICLE =
        "CREATE TABLE ${ArticleDbHelper.ArticleEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_AUTHOR} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_CONTENT} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_DESCRIPTION} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_PUBLISHED_AT} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_TITLE} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_URL} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_URL_TO_IMAGE} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_SOURCE_ID} TEXT, " +
                "${ArticleDbHelper.ArticleEntry.COLUMN_NAME_SOURCE_NAME} TEXT)"

    const val SQL_DELETE_CAPTURED_ENTRIES = "DROP TABLE IF EXISTS ${ArticleDbHelper.ArticleEntry.TABLE_NAME}"

}