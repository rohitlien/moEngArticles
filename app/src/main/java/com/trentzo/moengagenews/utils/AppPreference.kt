package com.trentzo.moengagenews.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.trentzo.moengagenews.storage.sqllite.StorageConstants

object AppPreference {

    private var sharedPreference: SharedPreferences? = null

    /**
     * This method should be called at least once to set the SharedPreferences,
     * preferably at application launch. Once called, it need not be called
     * again by subsequent Activities
     *
     * @param applicationContext : Must be the application context and not an Activity context
     */
    fun initPreferences(applicationContext: Context) {
        if (applicationContext is Activity) {
            throw IllegalArgumentException(StorageConstants.MSG_ILLEGAL_ARGS)
        } else if (sharedPreference == null) {
            sharedPreference = applicationContext.getSharedPreferences(StorageConstants.PREFERENCE_KEY, Context.MODE_PRIVATE)
        }
    }

    @Synchronized
    fun getPreference(preference: String): String? {
        return if (sharedPreference == null) null else sharedPreference!!.getString(preference, StorageConstants.VALUE_NOT_SET)
    }

    @Synchronized
    fun setPreference(preference: String, value: String) {
        if (sharedPreference == null)
            return
        sharedPreference!!.edit().putString(preference, value).apply()
    }

    @Synchronized
    fun removePreference(preference: String) {
        if (sharedPreference == null)
            return
        sharedPreference!!.edit().remove(preference).apply()
    }

    @Synchronized
    fun clearPreference() {
        if (sharedPreference == null)
            return
        sharedPreference!!.edit().clear().apply()
    }

    @Synchronized
    fun saveBooleanPreference(key: String, check: Boolean) {
        if (sharedPreference == null)
            return
        sharedPreference!!.edit().putBoolean(key, check).apply()
    }

    @Synchronized
    fun getBooleanPreference(key: String): Boolean {
        if (sharedPreference == null)
            return false
        return sharedPreference!!.getBoolean(key, false)
    }


    @Synchronized
    fun saveIntegerPreference(key: String, value: Int) {
        if (sharedPreference == null)
            return
        sharedPreference!!.edit().putInt(key, value).apply()
    }

    @Synchronized
    fun getIntegerPreference(key: String): Int {
        if (sharedPreference == null)
            return 0
        return sharedPreference!!.getInt(key, 0)
    }


    /**
     * Save user details in shared preference
     */

}