package com.trentzo.moengagenews.utils

import android.app.Application
import com.trentzo.moengagenews.storage.sqllite.MoEngageSQLiteOpenHelper

class AppController : Application() {

    companion object {
        lateinit var instance: AppController
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        MoEngageSQLiteOpenHelper(this)
        AppPreference.initPreferences(this)
    }

}